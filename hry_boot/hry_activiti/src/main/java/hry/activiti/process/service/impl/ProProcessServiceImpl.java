/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:55:47
 */
package hry.activiti.process.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.activiti.process.model.ProBusTable;
import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.ProDefineBtn;
import hry.activiti.process.model.ProProcess;
import hry.activiti.process.model.vo.HryTask;
import hry.activiti.process.service.*;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.config.remote.AdminRemoteService;
import hry.config.remote.AdminRemoteServiceUtil;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.util.date.DateUtil;
import hry.util.flowModel.FlowParams;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> ProProcessService </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:55:47
 */
@Slf4j
@Service("proProcessService")
public class ProProcessServiceImpl extends BaseServiceImpl<ProProcess, Long> implements ProProcessService {

	private final String startMethod = "Service.start";

	@Resource(name = "proProcessDao")
	@Override
	public void setDao (BaseDao<ProProcess, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	ActivitiService activitiService;

	@Autowired
	AdminRemoteService adminRemoteService;

	@Autowired
	ProDefineService proDefineService;

	@Autowired
	ProBusTableService proBusTableService;

	@Autowired
	ProDefineBtnService proDefineBtnService;

	@Autowired
	ProDefineNodeService proDefineNodeService;

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	ProProcessService proProcessService;

	@Autowired
	ProDefineFormService proDefineFormService;

	@Autowired
	AdminRemoteServiceUtil adminRemoteServiceUtil;


	@Override
	public JsonResult start(FlowParams flowParams) {

		if(flowParams==null){
			return new JsonResult().setMsg("flowParams is Null");
		}

		if(StringUtils.isEmpty(flowParams.getDefineKey())){
			return new JsonResult().setMsg("defineKey is Null");
		}

		if(StringUtils.isEmpty(flowParams.getStartUserId())){
			return new JsonResult().setMsg("startUserId is Null");
		}

		ProDefine proDefine = proDefineService.getByDefineKey(flowParams.getDefineKey());
		if(proDefine==null){
			return new JsonResult().setMsg("defineKey is error");
		}
		ProBusTable proBusTable = proBusTableService.get(proDefine.getBusTableId());
		if(proBusTable==null){
			return new JsonResult().setMsg("流程业务主表不存在");
		}

		//默认的启动方法 start方法
		String controlName = proBusTable.getJavaName()+ startMethod;
		flowParams.setControlName(controlName);
		flowParams.setDefineName(proDefine.getName());
		flowParams.setDefineVersion(proDefine.getVersion());

		String resutlStr = adminRemoteService.startFlow(JSON.toJSONString(flowParams));
		if(StringUtils.isEmpty(resutlStr)){
			return new JsonResult().setMsg("admin接口错误");
		}else{
			FlowParams FlowParams = JSON.parseObject(resutlStr, FlowParams.class);
			if(!FlowParams.getSuccess()){
				return new JsonResult().setMsg(FlowParams.getMsg());
			}

			//保存流程实例
			ProProcess proProcess = new ProProcess();
			proProcess.setPdKey(proDefine.getPdKey());
			proProcess.setVersion(proDefine.getVersion());
			proProcess.setDefineId(proDefine.getId());
			//流程名称
			proProcess.setName("【"+flowParams.getDefineName()+"】"+FlowParams.getProjectName());
			proProcess.setState(1);//运行中
			proProcessService.save(proProcess);

			//绑定流程表id
			FlowParams.setProcessId(proProcess.getId());
			//启动流程--传出FlowParams中的所有参数到流程参数中
			ProcessInstance processInstance = activitiService.start(flowParams.getStartUserId(), flowParams.getDefineKey(), FlowParams.getAllAttribute());
			if(processInstance!=null) {
				//更新流程表绑定流程实例ID
				proProcess.setPiId(processInstance.getId());
				update(proProcess);

				//启动返回第一个流程任务
				HryTask fisrtTask = activitiService.getFisrtTask(processInstance.getId());

				return new JsonResult().setSuccess(true).setObj(fisrtTask);
			}
		}
		return new JsonResult();
	}

	@Override
	public PageResult findMyTask(Integer page, Integer pageSize, Long userId) {
		List<HryTask> taskList = activitiService.findMyTask(page,pageSize,userId.toString());
		viewHryTask(taskList);
		Long count = activitiService.findMyTaskCount(userId.toString());
		return new PageResult(taskList,count,page,pageSize);
	}

	@Override
	public PageResult findAllTask(Integer page, Integer pageSize) {
		List<HryTask> taskList = activitiService.finaAllTask(page,pageSize);
		viewHryTask(taskList);
		Long count = activitiService.finaAllTaskCount();
		return new PageResult(taskList,count,page,pageSize);
	}

	/**
	 * 渲染
	 * @param taskList
	 */
	private void viewHryTask(List<HryTask> taskList){
		if(taskList!=null&&taskList.size()>0){
			for(HryTask hryTask : taskList){
				ProProcess proProcess = get(new QueryFilter(ProDefine.class).addFilter("piId=", hryTask.getPiId()));
				try {
					Object projectId = runtimeService.getVariable(proProcess.getPiId(), "projectId");
					if (projectId != null) {
						hryTask.setProjectId(Long.valueOf(projectId.toString()));
					}
				}catch (Exception e) {

				}

				//查询人员名称
				if(!StringUtils.isEmpty(hryTask.getAssigneeId())){
					hryTask.setAssigneeName(adminRemoteServiceUtil.getUserName(hryTask.getAssigneeId()));
				}

				hryTask.setProcessId(proProcess.getId());
				hryTask.setFlowName(proProcess.getName());


				if(hryTask.getDueDate()!=null){
					long time = hryTask.getDueDate().getTime()-System.currentTimeMillis();
					//计算倒计时
					hryTask.setCountDownTime(DateUtil.timestamp2Date(time));
					if(time<=0){//标记过期
						hryTask.setHasDue(true);
					}

				}
			}
		}
	}

	@Override
	public JsonResult completeTask(String taskId, Long btnId, FlowParams flowParams) {

		ProDefineBtn proDefineBtn = proDefineBtnService.get(btnId);
		if(proDefineBtn==null){
			return  new JsonResult().setMsg("流程按钮不存在");
		}

		//1通过
        if(ProDefineBtn.BUTYPE_1==proDefineBtn.getBtnType()){
            //控制器
			flowParams.setControlName(proDefineBtn.getControlName());
			flowParams.setProjectId((Long)activitiService.getVariable(flowParams.getPiId(),"projectId"));
            //远程调用业务方法
            String resutlStr = adminRemoteService.completeTask(JSON.toJSONString(flowParams));
            if(StringUtils.isEmpty(resutlStr)){
                return new JsonResult().setMsg("admin接口错误");
            }else{
                JsonResult jsonResult = JSON.parseObject(resutlStr, JsonResult.class);
                if(!jsonResult.getSuccess()){
                    if(!StringUtils.isEmpty(jsonResult.getMsg())) {
                        return new JsonResult().setMsg(jsonResult.getMsg());
                    }else{
                        return new JsonResult().setMsg("节点处理方法错误");
                    }
                }
            }


            //完成任务
            activitiService.completeTask(taskId);
            return new JsonResult().setSuccess(true);
        }
        //2拒绝
        if(ProDefineBtn.BUTYPE_2==proDefineBtn.getBtnType()){
            activitiService.stop(taskId);
            return new JsonResult().setSuccess(true);
        }
        //3打回
        if(ProDefineBtn.BUTYPE_3==proDefineBtn.getBtnType()){
            activitiService.skipLast(taskId);
            return new JsonResult().setSuccess(true);
        }
        //4打回至自定义
        if(ProDefineBtn.BUTYPE_4==proDefineBtn.getBtnType()){
			activitiService.skip(taskId,flowParams.getBackNode());
			return new JsonResult().setSuccess(true);
        }

        return new JsonResult();

	}

	@Override
	public PageResult findMyHisTask(Integer page, Integer pageSize, Long userId) {
		List<HryTask> taskList = activitiService.findMyHisTask(page,pageSize,userId.toString());
		viewHryTask(taskList);
		Long count = activitiService.findMyHisTaskCount(userId.toString());
		return new PageResult(taskList,count,page,pageSize);
	}

	@Override
	public JsonResult getTaskConfig(String taskId,String nodeKey,Long processId) {

		JSONObject taskConfig = new JSONObject();
		taskConfig.put("taskId",taskId);
		taskConfig.put("nodeKey",nodeKey);
		taskConfig.put("processId",processId);

		ProProcess proProcess = get(processId);

		ProDefine prodefine = proDefineService.getByDefineKey(proProcess.getPdKey());
		if(prodefine==null){
			return new JsonResult().setMsg("流程定义不存在");
		}
		ProBusTable proBusTable = proBusTableService.get(prodefine.getBusTableId());
		if(proBusTable==null){
			return new JsonResult().setMsg("流程业务主表不存在");
		}

		//获取流程配置
		Map<String,Object> map =  proDefineNodeService.getConfigByProcess(taskId,nodeKey, proProcess);
		taskConfig.putAll(map);

		//获取项目ID
		Object projectId = activitiService.getVariable(proProcess.getPiId(),"projectId");
		taskConfig.put("proejctId",projectId);

		//封装回显参数
		FlowParams flowParams = new FlowParams();
		flowParams.setTaskId(taskId);
		flowParams.setNodeKey(nodeKey);
		flowParams.setProcessId(processId);
		flowParams.setControlName(proBusTable.getJavaName()+"Service.view");
		flowParams.setProjectId(Long.valueOf(projectId.toString()));
		flowParams.setSonForm( proDefineFormService.getAllSonKey(nodeKey,proProcess.getPdKey(),proProcess.getVersion()));

		System.out.println(JSON.toJSONString(flowParams));
		//请求回显内容
		String resutlStr = adminRemoteService.viewFlow(JSON.toJSONString(flowParams));
		log.info("回显参数\r\n"+resutlStr);
		if(StringUtils.isEmpty(resutlStr)){
			return new JsonResult().setMsg("admin接口错误");
		}else{
			FlowParams FlowParams = JSON.parseObject(resutlStr, FlowParams.class);
			if(!FlowParams.getSuccess()){
				return new JsonResult().setMsg(FlowParams.getMsg());
			}

			taskConfig.put("view",FlowParams.getAllAttribute());
		}


		return new JsonResult().setSuccess(true).setObj(taskConfig);
	}
}
