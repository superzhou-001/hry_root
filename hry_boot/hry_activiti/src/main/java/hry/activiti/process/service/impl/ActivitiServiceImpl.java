package hry.activiti.process.service.impl;

import com.alibaba.fastjson.JSONObject;
import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.model.vo.HryTask;
import hry.activiti.process.service.ActivitiService;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefineService;
import hry.activiti.process.service.ProProcessService;
import hry.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@Slf4j
public class ActivitiServiceImpl implements ActivitiService {


    @Autowired
    RepositoryService repositoryService;

    @Autowired
    TaskService taskService;


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    @Autowired
    ProDefineService proDefineService;

    @Autowired
    ProProcessService proProcessService;

    @Autowired
    ProDefineNodeService proDefineNodeService;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    IdentityService identityService;


    @Override
    public ProcessDefinition deploy(ProDefine proDefine) {

        //发布
        DeploymentBuilder builder = repositoryService.createDeployment();
        //名称+.bpmn
        builder.name(proDefine.getName() + ".bpmn");
        builder.key(proDefine.getDefineKey());
        builder.addString(proDefine.getName() + ".bpmn", HtmlUtils.htmlUnescape(proDefine.getDefineText()));
        Deployment deploy = builder.deploy();

        //查询流程部署信息
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).latestVersion().singleResult();

        return pd;
    }

    @Override
    public ProcessInstance start(Long startUserId, String pdKey, Map<String, Object> map) {

        map.put("startUserId", startUserId);
        //启动流程--设置流程参数
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(pdKey, map);

        if (processInstance != null) {
            //设置第一个任务为发起人
            String assignee = "1";
            if (startUserId != null) {
                assignee = startUserId.toString();
            }

            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
            //设置任务执行人
            taskSetAssignee(task.getId(), assignee);

            //获取节点配置
            ProDefineNode node = proDefineNodeService.getNode(task.getTaskDefinitionKey(), pdKey, Integer.valueOf(map.get("defineVersion").toString()));
            if (node != null && node.getTimeDay() != null) {
                //设置任务过期时间
                taskService.setDueDate(task.getId(), DateUtil.addDay(new Date(), node.getTimeDay()));
            } else {
                //默认3天
                taskService.setDueDate(task.getId(), DateUtil.addDay(new Date(), 3));
            }

            return processInstance;
        }

        return null;
    }


    public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished().processInstanceId(processInstanceId).count() > 0;
    }


    @Override
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String piId) {


        /**
         * 获得当前活动的节点
         */
        String processDefinitionId = "";
        if (this.isFinished(piId)) {// 如果流程已经结束，则得到结束节点
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(piId).singleResult();

            processDefinitionId = pi.getProcessDefinitionId();
        } else {// 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(piId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }
        List<String> highLightedActivitis = new ArrayList<String>();

        /**
         * 获得活动的节点
         */
        List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(piId).orderByHistoricActivityInstanceStartTime().asc().list();

        for (HistoricActivityInstance tempActivity : highLightedActivitList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }

        List<String> flows = new ArrayList<>();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();

        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
//        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, flows, "宋体", "宋体", "宋体", engconf.getClassLoader(), 1.0, false);


        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            httpServletResponse.setContentType("image/png;charset=UTF-8");
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } catch (IOException e) {
            log.error("操作异常", e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }

    @Override
    public void completeTask(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (false) {//是否转会签节点

//            toCountersign(task.getProcessDefinitionId(), taskId);
            Map<String, Object> map = new HashMap<>();
            String[] arr = {"2", "b1", "b2", "b3", "b4" };
            map.put("assigneeList", Arrays.asList(arr));
            // assigneeList
            taskService.complete(taskId, map);
        } else {
            //完成任务
            taskService.complete(taskId);
            //设置下一任务执行人
            taskListSetAssignee(task.getProcessInstanceId());
        }


    }

    @Override
    public HryTask getFisrtTask(String piId) {

        Task task = taskService.createTaskQuery().processInstanceId(piId).singleResult();
        if (task != null) {
            HryTask hryTask = new HryTask();
            hryTask.setTaskId(task.getId());
            hryTask.setTaskName(task.getName());
            hryTask.setCreateTime(task.getCreateTime());
            hryTask.setAssigneeId(task.getAssignee());
            hryTask.setAssigneeName(task.getAssignee());
            hryTask.setPdId(task.getProcessDefinitionId());
            hryTask.setPiId(task.getProcessInstanceId());
            hryTask.setExecutionId(task.getExecutionId());
            hryTask.setNodeKey(task.getTaskDefinitionKey());
            hryTask.setDueDate(task.getDueDate());

            Object projectId = runtimeService.getVariable(piId, "projectId");
            Object projectName = runtimeService.getVariable(piId, "projectName");
            Object processId = runtimeService.getVariable(piId, "processId");
            hryTask.setProcessId(Long.valueOf(processId.toString()));
            hryTask.setFlowName(projectName.toString());
            hryTask.setProjectId(Long.valueOf(projectId.toString()));
            return hryTask;
        }

        return null;
    }


    /**
     * 批量任务设置任务执行人
     *
     * @param piId
     */
    private void taskListSetAssignee(String piId) {
        //查询当前流程所有任务
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(piId).list();

        if (taskList == null || taskList.size() < 1) {
            return;
        }
        //批量设置流程处理人
        for (Task task : taskList) {
            taskSetAssignee(task.getId(), proDefineNodeService.findAssigneeUser(task.getTaskDefinitionKey(), task.getProcessInstanceId()));
        }
    }

    @Override
    public List<HryTask> findMyTask(Integer page, Integer pageSize, String assigneeId) {


        List<Task> list = taskService//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
//                .taskAssignee(assigneeId)//指定个人任务查询，指定办理人
                .taskAssigneeLikeIgnoreCase(assigneeId)
//						.taskCandidateUser(candidateUser)//组任务的办理人查询

//						.executionId(executionId)//使用执行对象ID查询
                /**排序*/
                .orderByTaskCreateTime().desc()//使用创建时间的升序排列
                /**返回结果集*/
//						.singleResult()//返回惟一结果集
//						.count()//返回结果集的数量
                .listPage(((page - 1) * pageSize), pageSize);//分页查询
//                .list();//返回列表
        if (list != null && list.size() > 0) {

            List<HryTask> resultList = new ArrayList<>();

            for (Task task : list) {
                HryTask hryTask = new HryTask();
                hryTask.setTaskId(task.getId());
                hryTask.setTaskName(task.getName());
                hryTask.setCreateTime(task.getCreateTime());
                hryTask.setAssigneeId(task.getAssignee());
                hryTask.setPdId(task.getProcessDefinitionId());
                hryTask.setPiId(task.getProcessInstanceId());
                hryTask.setExecutionId(task.getExecutionId());
                hryTask.setNodeKey(task.getTaskDefinitionKey());
                hryTask.setDueDate(task.getDueDate());
                resultList.add(hryTask);
            }

            return resultList;
        }

        return null;

    }

    @Override
    public Long findMyTaskCount(String assigneeId) {
        return taskService//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssigneeLike(assigneeId)//指定个人任务查询，指定办理人
                /**排序*/
                .orderByTaskCreateTime().desc()//使用创建时间的升序排列
                /**返回结果集*/
//						.singleResult()//返回惟一结果集
                .count();//返回结果集的数量
    }

    @Override
    public List<HryTask> finaAllTask(Integer page, Integer pageSize) {
        List<Task> list = taskService//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                .orderByTaskCreateTime().desc()//使用创建时间的升序排列
                /**返回结果集*/
//						.singleResult()//返回惟一结果集
//						.count()//返回结果集的数量
                .listPage(((page - 1) * pageSize), pageSize);//分页查询
//                .list();//返回列表
        if (list != null && list.size() > 0) {
            List<HryTask> resultList = new ArrayList<>();

            for (Task task : list) {
                HryTask hryTask = new HryTask();
                hryTask.setTaskId(task.getId());
                hryTask.setTaskName(task.getName());
                hryTask.setCreateTime(task.getCreateTime());
                hryTask.setAssigneeId(task.getAssignee());
                hryTask.setPdId(task.getProcessDefinitionId());
                hryTask.setPiId(task.getProcessInstanceId());
                hryTask.setExecutionId(task.getExecutionId());
                hryTask.setNodeKey(task.getTaskDefinitionKey());
                resultList.add(hryTask);
            }

            return resultList;
        }

        return null;
    }

    @Override
    public Long finaAllTaskCount() {
        return taskService//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                .orderByTaskCreateTime().desc()//使用创建时间的升序排列
                .count();//返回结果集的数量
    }

    @Override
    public List<HryTask> findMyHisTask(Integer page, Integer pageSize, String assigneeId) {

        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assigneeId)
                .finished()
                .orderByTaskCreateTime().desc()
                .listPage(((page - 1) * pageSize), pageSize);

        if (list != null && list.size() > 0) {

            List<HryTask> resultList = new ArrayList<>();

            for (HistoricTaskInstance task : list) {
                HryTask hryTask = new HryTask();
                hryTask.setTaskId(task.getId());
                hryTask.setTaskName(task.getName());
                hryTask.setCreateTime(task.getCreateTime());
                hryTask.setAssigneeId(task.getAssignee());
                hryTask.setAssigneeName(task.getAssignee());
                hryTask.setPdId(task.getProcessDefinitionId());
                hryTask.setPiId(task.getProcessInstanceId());
                hryTask.setExecutionId(task.getExecutionId());
                hryTask.setNodeKey(task.getTaskDefinitionKey());
                resultList.add(hryTask);


            }

            return resultList;
        }

        return null;
    }

    @Override
    public Long findMyHisTaskCount(String assigneeId) {
        return historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assigneeId)
                .finished()
                .count();
    }

    @Override
    public void stop(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "");
    }

    @Override
    public void skipLast(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(task.getTaskDefinitionKey());
        SequenceFlow sequenceFlow = flowNode.getIncomingFlows().get(0);
        // 获取上一个节点的activityId
        String sourceRef = sequenceFlow.getSourceRef();
        if (sourceRef.contains("Gateway")) {//如果上一节点是网关
            FlowNode gatewayNode = (FlowNode) sequenceFlow.getSourceFlowElement();
            sourceRef = gatewayNode.getIncomingFlows().get(0).getSourceRef();
        }
        System.out.println("跳转节点" + sourceRef);
        runtimeService.createChangeActivityStateBuilder().processInstanceId(task.getProcessInstanceId()).moveActivityIdTo(task.getTaskDefinitionKey(), sourceRef).changeState();

        //批量设置流程处理人
        taskListSetAssignee(task.getProcessInstanceId());
    }

    @Override
    public void skip(String taskId, String nodeKey) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        runtimeService.createChangeActivityStateBuilder().processInstanceId(task.getProcessInstanceId()).moveActivityIdTo(task.getTaskDefinitionKey(), nodeKey).changeState();
        //批量设置流程处理人
        taskListSetAssignee(task.getProcessInstanceId());
    }

    @Override
    public List<FlowElement> findAllNode(String taskId) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();

        List<FlowElement> list = new ArrayList(flowElements);

        return list;

    }

    @Override
    public List<JSONObject> findFlowLog(String piId) {
        List<JSONObject> logList = new ArrayList<>();
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(piId)
                .list();
        if (list != null && list.size() > 0) {

            for (HistoricTaskInstance hti : list) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("taskId", hti.getId());
                jsonObject.put("name", hti.getName());
                jsonObject.put("pdId", hti.getProcessDefinitionId());
                jsonObject.put("assignee", hti.getAssignee());
                jsonObject.put("assigneeName", hti.getAssignee());
                jsonObject.put("endTime", hti.getEndTime());
                jsonObject.put("startTime", hti.getStartTime());
                logList.add(jsonObject);
            }
        }
        return logList;
    }

    @Override
    public void taskSetAssignee(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }

    @Override
    public Object getVariable(String piId, String paramsName) {
        return runtimeService.getVariable(piId, paramsName);
    }


    /**
     * 普通节点转会签节点.
     *
     * @param processDefinitionId 流程定义ID.
     * @param taskId              当前节点ID.
     * @return: void
     */
    public void toCountersign(String processDefinitionId, String taskId) {


        // 根据任务ID获取当前任务对象.
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (null == task) {

        }

        // 根据流程定义ID获取流程bpmnModel.
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        // 获取当前流程对象.
        Process process = bpmnModel.getProcesses().get(0);
        // 根据当前节点ID获取对应任务节点对象.
        UserTask currenUserTask = (UserTask) process.getFlowElement(task.getTaskDefinitionKey());
        // 获取当前节点出线信息.
        SequenceFlow sequenceFlow = currenUserTask.getOutgoingFlows().get(0);
        // 根据当前节点出线信息获取下一节点元素.
        FlowElement flowElement = process.getFlowElement(sequenceFlow.getTargetRef());
        // 判断下一节点元素是否为任务节点.
        if (!(flowElement instanceof UserTask)) {

        }
        // 将下一节点元素转换为任务节点对象.
        UserTask nextUserTask = (UserTask) flowElement;

        // 配置会签所需条件.
        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        // 配置节点人员办理顺序 串行:true 并行:false.
        multiInstanceLoopCharacteristics.setSequential(false);
        // 配置会签集合变量名称.
        multiInstanceLoopCharacteristics.setCollectionString("assigneeList");
        // 配置会签集合遍历名称.
        multiInstanceLoopCharacteristics.setElementVariable("assignee");

        // 设置下一节点处理人表达式 引用会签条件activiti:elementVariable="assignee".
        nextUserTask.setAssignee("${assignee}");

        // 下一任务节点设置会签循环特征.
        nextUserTask.setLoopCharacteristics(multiInstanceLoopCharacteristics);

        // 获取流程引擎配置实现类.
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) ProcessEngines.getDefaultProcessEngine().getProcessEngineConfiguration();
        // 创建新的任务实例.
        UserTaskActivityBehavior userTaskActivityBehavior = processEngineConfiguration.getActivityBehaviorFactory().createUserTaskActivityBehavior(nextUserTask);
        // 创建BPMN 2.0规范中描述的多实例功能.
        ParallelMultiInstanceBehavior behavior = new ParallelMultiInstanceBehavior(nextUserTask, userTaskActivityBehavior);
        // 设置多实例元素变量.
        behavior.setCollectionElementVariable("assignee");
        // 注入表达式.
        ExpressionManager expressionManager = processEngineConfiguration.getExpressionManager();
        // 设置多实例集合表达式.
        behavior.setCollectionExpression(expressionManager.createExpression("assigneeList"));
        // 设置下一节点多实例行为.
        nextUserTask.setBehavior(behavior);

    }

}
