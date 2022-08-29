package hry.activiti.process.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.config.remote.AdminRemoteService;
import hry.core.mvc.controller.BaseController;
import hry.security.jwt.annotation.UnAuthentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Api(value = "BPMN流程接口", tags = "BPMN流程接口", description = "BPMN流程接口")
@RestController
@RequestMapping("/bpmn")
@CrossOrigin
public class BpmnController extends BaseController {

    @Autowired
    AdminRemoteService adminRemoteService;

    @ApiOperation(value = "测试feign", notes = "测试feign")
    @PostMapping("/hello")
    @UnAuthentication
    public JsonResult hello(HttpServletRequest request) {

        System.out.println(adminRemoteService.getAppUser(1L));

        return new JsonResult(adminRemoteService.getAppUser(1L));
    }



    @ApiOperation(value = "测试流程数据是否正确", notes = "测试流程数据是否正确")
    @PostMapping("/testBpmn")
    @UnAuthentication
    public JsonResult testBpmn(HttpServletRequest request) {

        BpmnModel bpmnModel = new BpmnModel();


        String define = request.getParameter("define");
        JSONObject jsonObject = JSON.parseObject(define);
        Set<String> keySet = jsonObject.keySet();

        //开始节点
        StartEvent startEvent = null;
        //结束节点
        EndEvent endEvent = null;

        //任务节点List
        List<UserTask> userTaskList = new ArrayList<>();
        //连接节点List
        List<SequenceFlow> sequenceFlowList = new ArrayList<>();

        //连线起点集合
        Set<String> fromIdSet = new HashSet<>();
        //连线终点集合
        Set<String> toIdSet = new HashSet<>();




        for(String key : keySet){
            JSONObject node = jsonObject.getJSONObject(key);
            if("startEvent".equals(node.getString("groupName"))){
                startEvent = new StartEvent();
                startEvent.setId(key);
                startEvent.setName(node.getString("text"));
            }

            if("endEvent".equals(node.getString("groupName"))){
                //结束节点属性
                endEvent = new EndEvent();
                endEvent.setId(key);
                endEvent.setName(node.getString("text"));
            }

            if("task".equals(node.getString("groupName"))) {
                //普通的UserTask节点
                UserTask userTask = new UserTask();
                userTask.setId(key);
                userTask.setName(node.getString("text"));
                userTaskList.add(userTask);
            }

            //连线
            if("linker".equals(node.getString("name"))){

                String fromId = node.getJSONObject("from").getString("id");
                String toId = node.getJSONObject("to").getString("id");

                fromIdSet.add(fromId);
                toIdSet.add(toId);

                SequenceFlow s1=new SequenceFlow();
                s1.setId(key);
                s1.setName(node.getString("text"));
                s1.setSourceRef(fromId);
                s1.setTargetRef(toId);
                sequenceFlowList.add(s1);
            }
        }

        //封装起点
        for(String fromId : fromIdSet){
            List<SequenceFlow> sfList =new ArrayList<SequenceFlow>();
            for(SequenceFlow sequenceFlow : sequenceFlowList){
                if(sequenceFlow.getSourceRef().equals(fromId)){
                    sfList.add(sequenceFlow);
                }
            }

            //判断开始节点id是否和连接开启节点相同
            if(startEvent.getId().equals(fromId)){
                startEvent.setOutgoingFlows(sfList);
            }

            for(UserTask userTask : userTaskList){
                if(userTask.getId().equals(fromId)){
                    userTask.setOutgoingFlows(sfList);
                }
            }
        }

        //封装终点
        for(String toId : toIdSet){
            List<SequenceFlow> sfList =new ArrayList<SequenceFlow>();
            for(SequenceFlow sequenceFlow : sequenceFlowList){
                if(sequenceFlow.getTargetRef().equals(toId)){
                    sfList.add(sequenceFlow);
                }
            }

            //判断开始节点id是否和连接开启节点相同
            if(endEvent.getId().equals(toId)){
                endEvent.setIncomingFlows(sfList);
            }

            for(UserTask userTask : userTaskList){
                if(userTask.getId().equals(toId)){
                    userTask.setIncomingFlows(sfList);
                }
            }
        }

        //Process对象
        Process process= new Process();
        process.setId("process1");
        //添加节点
        process.addFlowElement(startEvent);
        //添加任务节点
        for(UserTask userTask : userTaskList){
            process.addFlowElement(userTask);
        }
        //添加连线
        for(SequenceFlow sequenceFlow : sequenceFlowList){
            process.addFlowElement(sequenceFlow);
        }
        //添加终点
        process.addFlowElement(endEvent);

        bpmnModel.addProcess(process);


        //生成自动布局
        new BpmnAutoLayout(bpmnModel).execute();




        //bpmnModel 转换为标准的bpmn xml文件
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML =  bpmnXMLConverter.convertToXML(bpmnModel);
        String bytes = new String(convertToXML);
        System.out.println(bytes);


        //验证bpmnModel 是否是正确的bpmn xml文件
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();

        ProcessValidator defaultProcessValidator =  processValidatorFactory.createDefaultProcessValidator();
        //验证失败信息的封装ValidationError
        List<ValidationError> validate =  defaultProcessValidator.validate(bpmnModel);

        System.out.println(validate.size());

        return new JsonResult().setSuccess(true);
    }


}
