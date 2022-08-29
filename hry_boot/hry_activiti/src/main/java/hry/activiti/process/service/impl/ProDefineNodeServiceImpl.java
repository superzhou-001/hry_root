/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:21
 */
package hry.activiti.process.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.activiti.process.model.*;
import hry.activiti.process.service.*;
import hry.bean.JsonResult;
import hry.config.remote.AdminRemoteService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> ProDefineNodeService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:21
 */
@Service("proDefineNodeService")
public class ProDefineNodeServiceImpl extends BaseServiceImpl<ProDefineNode, Long> implements ProDefineNodeService {

	@Resource(name = "proDefineNodeDao")
	@Override
	public void setDao (BaseDao<ProDefineNode, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	ProDefineService proDefineService;

	@Autowired
	ProDocumentClassService proDocumentClassService;

	@Autowired
	ProDefinePersonService proDefinePersonService;

	@Autowired
	ProDefineBtnService proDefineBtnService;

	@Autowired
	ProDefineNodeService proDefineNodeService;

	@Autowired
	ProDefineFormService proDefineFormService;

	@Autowired
	ProDefineNoticeService proDefineNoticeService;

	@Autowired
	AdminRemoteService adminRemoteService;

	@Autowired
	ProPersonGroupService proPersonGroupService;

	@Autowired
	ProMainFormService proMainFormService;

	@Autowired
	ProSonFormService proSonFormService;

	@Autowired
	ProDefineDocumentService proDefineDocumentService;

	@Autowired
	ProDocumentService proDocumentService;

	@Autowired
	ProProcessService  proProcessService;

	@Autowired
	ActivitiService activitiService;

	@Autowired
	ProDocumentItemService proDocumentItemService;

	@Override
	public Map<String,Object> getConfig(String defineKey, String nodeKey) {


		ProDefine proDefine = proDefineService.get(new QueryFilter(ProDefine.class).addFilter("defineKey=", defineKey));

		if(proDefine!=null){
			Map<String,Object> map = new HashMap();
			//获取节点
			ProDefineNode node = get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", defineKey).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));

			List<ProDefinePerson> personList = proDefinePersonService.find(new QueryFilter(ProDefinePerson.class).addFilter("nodeId=", node.getId()));
			if(personList!=null&&personList.size()>0){
				for(ProDefinePerson person: personList ){
					person.setPersonName(proDefinePersonService.getPersonName(person));
				}
			}

			List<ProDefineBtn> btn = proDefineBtnService.find(new QueryFilter(ProDefineBtn.class).addFilter("nodeId=", node.getId()));

			List<ProDefineForm> formList = proDefineFormService.find(new QueryFilter(ProDefineForm.class).addFilter("nodeId=", node.getId()).addFilter("type=",1));
			if(formList!=null&&formList.size()>0){
				for(ProDefineForm form : formList){
					if(ProDefineForm.TYPE_1==form.getType()){

						ProMainForm proMainForm = proMainFormService.get(form.getFormId());
						if(proMainForm!=null) {
							form.setFormName(proMainForm.getViewName());
						}
					}
				}
			}

			List<ProDefineNotice> notice = proDefineNoticeService.find(new QueryFilter(ProDefineNotice.class).addFilter("nodeId=", node.getId()));


			List<ProDefineDocument> documentList = proDefineDocumentService.find(new QueryFilter(ProDefineDocument.class).addFilter("nodeId=", node.getId()));


			map.put("node",node);
			map.put("person",personList);
			map.put("btn",btn);
			map.put("form",formList);
			map.put("notice",notice);
			map.put("document",documentList);
			return map;
		}

		return null;
	}

	@Override
	public Map<String, Object> getConfigByProcess(String taskId,String nodeKey,ProProcess proProcess) {


		//获取节点
		ProDefineNode node = get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proProcess.getPdKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proProcess.getVersion()));

		Map<String,Object> map = new HashMap();

		//流程按钮
		List<ProDefineBtn> btn = proDefineBtnService.find(new QueryFilter(ProDefineBtn.class).addFilter("nodeId=", node.getId()));

		//流程表单
		List<ProDefineForm> formList = proDefineFormService.find(new QueryFilter(ProDefineForm.class).addFilter("nodeId=", node.getId()).addFilter("type=",ProDefineForm.TYPE_1));
		if(formList!=null&&formList.size()>0){
			for(ProDefineForm form : formList){
				ProMainForm proMainForm = proMainFormService.get(form.getFormId());
				if(proMainForm!=null) {
					form.setFormName(proMainForm.getViewName());
					form.setFormKey(proMainForm.getFormKey());
				}

				//查询子表单
				List<ProDefineForm> sonFormList = proDefineFormService.find(new QueryFilter(ProDefineForm.class).addFilter("nodeId=", node.getId()).addFilter("type=",ProDefineForm.TYPE_2));
				if(sonFormList!=null&&sonFormList.size()>0) {
					for (ProDefineForm sonForm : sonFormList) {
						ProSonForm proSonForm = proSonFormService.get(sonForm.getFormId());
						if (proSonForm != null) {
							sonForm.setFormName(proSonForm.getViewName());
							sonForm.setFormKey(proSonForm.getFormKey());
						}
					}
				}
				form.setSonFormList(sonFormList);
			}
		}

		//流程文档
		List<ProDefineDocument> defineDocumentList = proDefineDocumentService.find(new QueryFilter(ProDefineDocument.class).addFilter("nodeId=", node.getId()));
		if(defineDocumentList!=null&&defineDocumentList.size()>0){

			//材料信息list
			List<ProDocument> documentList = new ArrayList<>();

			JSONArray jsonArray = new JSONArray();

			//分类map
			Map<String,List<ProDocument>> documentListMap = new HashMap<>();

			//抓取所有材料，复制流程配置
			for(ProDefineDocument defineDocument : defineDocumentList){
				ProDocument proDocument = proDocumentService.get(new QueryFilter(ProDocument.class).addFilter("fileKey=", defineDocument.getFileKey()));
				//传递流程材料配置到材料上
				proDocument.setIsRequire(defineDocument.getIsRequire());
				proDocument.setViewModel(defineDocument.getViewModel());
				documentList.add(proDocument);
			}

			//分类
			for(ProDocument document : documentList){
				List<ProDocument> list = documentListMap.get(document.getClassId().toString());

				if(list!=null){
					list.add(document);
				}else{
					list = new ArrayList<>();
					list.add(document);
					documentListMap.put(document.getClassId().toString(),list);
				}
			}

			for(String id : documentListMap.keySet()){
				ProDocumentClass proDocumentClass = proDocumentClassService.get(Long.valueOf(id));
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name",proDocumentClass.getName());
				jsonObject.put("classId",proDocumentClass.getId());
				jsonObject.put("fileList",documentListMap.get(id));
				jsonArray.add(jsonObject);
			}


			map.put("document",jsonArray);
			map.put("hasDocument",true);
		}
		map.put("node",node);
		map.put("btn",btn);
		map.put("form",formList);

		//查询全部节点
		List<FlowElement> allNode = activitiService.findAllNode(taskId);
		JSONArray allNodeArr = new JSONArray();
		//渲染任务节点
		for(FlowElement flowElement : allNode){
			if (flowElement instanceof UserTask) {
				JSONObject nodeObj = new JSONObject();
				nodeObj.put("id",flowElement.getId());
				nodeObj.put("name",flowElement.getName());
				allNodeArr.add(nodeObj);
			}
		}
		map.put("allNode",allNodeArr);



		return map;

	}

	@Override
	public     JsonResult saveByFlow(Long defineId, String nodeKey, Integer nodeType, Integer timeType, Integer timeDay,Integer decidetype,Integer decideValue,String personValue) {
		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		node.setNodeType(nodeType);
		node.setTimeType(timeType);
		node.setTimeDay(timeDay);

		node.setDecideType(decidetype);
		node.setDecideValue(decideValue);

		//保存会签人员
		proDefinePersonService.saveMultiPerson(node.getId(),personValue);


		update(node);

		return new JsonResult().setSuccess(true);
	}

	@Override
	public String findAssigneeUser(String taskDefinitionKey, String processInstanceId) {
		//查询流程
		ProProcess proProcess = proProcessService.get(new QueryFilter(ProProcess.class).addFilter("piId=", processInstanceId));
		//查询节点配置
		ProDefineNode node = getNode(taskDefinitionKey, proProcess.getPdKey(), proProcess.getVersion());
		//查询节点配置人员Id
		return proDefinePersonService.getAssigneeUserId(processInstanceId,node);
	}



	@Override
	public ProDefineNode getNode(String nodeKey, String defineKey, Integer version) {
		ProDefineNode node = get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", defineKey).addFilter("nodeKey=", nodeKey).addFilter("version=", version));
		return node;
	}


}
