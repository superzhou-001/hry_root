/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 16:17:44
 */
package hry.business.cf.service;

import hry.bean.PageResult;
import hry.business.cf.model.CfFacilityFlow;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.util.flowModel.FlowParams;

/**
 * <p> CfFacilityFlowService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 16:17:44
 */
public interface CfFacilityFlowService extends BaseService<CfFacilityFlow, Long> {
    /**
     * 根据条件查询信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);

    /**
     * 买方授信__授信客户申请
     * @param flowParams
     * @return
     */
    FlowParams syedsq(FlowParams flowParams);

    /**
     * 买方授信__风险审核
     * @param flowParams
     * @return
     */
    FlowParams fxsh(FlowParams flowParams);

    /**
     * 买方授信__部门经理审核
     * @param flowParams
     * @return
     */
    FlowParams bmjlsh(FlowParams flowParams);

    /**
     * 买方授信__合同签属
     * @param flowParams
     * @return
     */
    FlowParams htqs(FlowParams flowParams);

    /**
     * 买方授信__融总经理审核
     * @param flowParams
     * @return
     */
    FlowParams fzjlsp(FlowParams flowParams);

    /**
     * 买方授信__总经理审批
     * @param flowParams
     * @return
     */
    FlowParams zjlsp(FlowParams flowParams);






    /**
     * 卖方授信__授信客户申请
     * @param flowParams
     * @return
     */
    FlowParams sellSyedsq(FlowParams flowParams);

    /**
     * 卖方授信__风险审核
     * @param flowParams
     * @return
     */
    FlowParams sellFxsh(FlowParams flowParams);

    /**
     * 卖方授信__部门经理审核
     * @param flowParams
     * @return
     */
    FlowParams sellBmjlsh(FlowParams flowParams);

    /**
     * 卖方授信__合同签属
     * @param flowParams
     * @return
     */
    FlowParams sellHtqs(FlowParams flowParams);

    /**
     * 卖方授信__融总经理审核
     * @param flowParams
     * @return
     */
    FlowParams sellFzjlsp(FlowParams flowParams);

    /**
     * 卖方授信__总经理审批
     * @param flowParams
     * @return
     */
    FlowParams sellZjlsp(FlowParams flowParams);

}
