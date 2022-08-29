/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-28 12:05:59
 */
package hry.platform.flow.service;

import hry.core.mvc.service.BaseService;
import hry.platform.flow.model.AppFlow;
import hry.util.flowModel.FlowParams;

/**
 * <p> AppFlowService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-28 12:05:59
 */
public interface AppFlowService extends BaseService<AppFlow, Long> {


    /**
     * 起床
     * @param flowParams
     * @return
     */
    FlowParams oneFlowQc(FlowParams flowParams);


    /**
     * 洗脸
     * @param flowParams
     * @return
     */
    FlowParams oneFlowXl(FlowParams flowParams);

    /**
     * 吃饭
     * @param flowParams
     * @return
     */
    FlowParams oneFlowCf(FlowParams flowParams);


    /**
     * 上班
     * @param flowParams
     * @return
     */
    FlowParams oneFlowSb(FlowParams flowParams);


    /**
     * 打代码
     * @param flowParams
     * @return
     */
    FlowParams oneFlowDdm(FlowParams flowParams);


    /**
     * 下班
     * @param flowParams
     * @return
     */
    FlowParams oneFlowXb(FlowParams flowParams);

    /**
     * 坐车
     * @param flowParams
     * @return
     */
    FlowParams oneFlowZc(FlowParams flowParams);

    /**
     * 坐飞机
     * @param flowParams
     * @return
     */
    FlowParams oneFlowZfj(FlowParams flowParams);

    /**
     * 坐坦克
     * @param flowParams
     * @return
     */
    FlowParams oneFlowZtc(FlowParams flowParams);

}
