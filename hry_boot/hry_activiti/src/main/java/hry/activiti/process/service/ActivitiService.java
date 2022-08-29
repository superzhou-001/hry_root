package hry.activiti.process.service;

import com.alibaba.fastjson.JSONObject;
import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.vo.HryTask;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ActivitiService {

    /**
     * 流程部署
     */
    ProcessDefinition deploy(ProDefine proDefine);

    /**
     * 启动流程
     * @param startUserId 发起人id
     * @param pdKey
     */
    ProcessInstance start(Long startUserId, String pdKey, Map<String,Object> params);

    /**
     * 获取流程图
     * @param httpServletResponse
     * @param processId
     */
    void genProcessDiagram(HttpServletResponse httpServletResponse, String piId);

    /**
     * 完成任务
     * @param taskId
     */
    void completeTask(String taskId);

    /**
     * 获取第一个任务
     * @return
     */
    HryTask getFisrtTask(String piId);

    /**
     * 查询我的任务
     * @return
     */
    List<HryTask> findMyTask(Integer page,Integer pageSize,String assigneeId);

    /**
     * 查询我的任务长度
     * @param assigneeId
     * @return
     */
    Long findMyTaskCount(String assigneeId);

    /**
     * 查询系统全部任务
     * @return
     */
    List<HryTask> finaAllTask(Integer page, Integer pageSize);

    /**
     * 查询全部任务的长度
     * @param assigneeId
     * @return
     */
    Long finaAllTaskCount();


    List<HryTask> findMyHisTask(Integer page, Integer pageSize, String assigneeId);

    Long findMyHisTaskCount(String assigneeId);

    /**
     * 终止流程
     * @param taskId
     */
    void stop(String taskId);

    /**
     * 跳到上一节点
     * @param taskId
     */
    void skipLast(String taskId);

    /**
     * 获取流程节点
     * @param taskId
     * @return
     */
    List<FlowElement> findAllNode(String taskId);


    /**
     * 查询流程日志
     * @param piId
     * @return
     */
    List<JSONObject> findFlowLog(String piId);

    /**
     * 设置任务执行人
     * @param taskId
     * @param userId
     */
    void taskSetAssignee(String taskId,String userId);

    /**
     * 获取单个流程参数
     * @param piId
     * @param paramsName
     * @return
     */
    Object getVariable(String piId,String paramsName);

    void skip(String taskId, String backNode);
}
