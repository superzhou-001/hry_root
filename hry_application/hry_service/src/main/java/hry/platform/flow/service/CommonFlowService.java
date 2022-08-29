package hry.platform.flow.service;

import hry.util.flowModel.FlowParams;

/**
 * 流程操作接口
 */
public interface CommonFlowService {

    /**
     * 启动流程
     */
    FlowParams start(FlowParams flowParams);



    /**
     * 获取流程页面信息，查看回显数据使用
     */
    FlowParams view(FlowParams flowParams);

}
