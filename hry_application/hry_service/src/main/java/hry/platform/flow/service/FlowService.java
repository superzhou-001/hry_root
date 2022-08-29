package hry.platform.flow.service;

import hry.bean.JsonResult;
import hry.util.flowModel.FlowParams;

/**
 * 底层流程处理接口--反射接口类
 */
public interface FlowService {


    /**
     * 启动流程
     */
    JsonResult startFlow(FlowParams flowParams);


    /**
     * 完成流程任务
     */
    JsonResult completeTask(FlowParams flowParams);


    /**
     * 反射执行流程实际方法
     * @param flowParams
     * @return
     */
    FlowParams execute(FlowParams flowParams);



}
