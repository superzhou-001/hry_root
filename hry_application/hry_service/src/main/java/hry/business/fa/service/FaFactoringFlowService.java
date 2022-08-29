/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 17:01:01
 */
package hry.business.fa.service;

import hry.bean.JsonResult;
import hry.business.fa.model.FaFactoringFlow;
import hry.core.mvc.service.BaseService;
import hry.util.flowModel.FlowParams;

/**
 * <p> FaFactoringFlowService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 17:01:01
 */
public interface FaFactoringFlowService extends BaseService<FaFactoringFlow, Long> {

    /**
     * 获取项目详情所有信息
     * @param id
     * @return
     */
    JsonResult getFaFactoringFlowInfo(Long id);


    /**
     * 尽职查询
     * @param flowParams
     * @return
     */
    FlowParams jzdc(FlowParams flowParams);

    /**
     * 风险审核
     * @param flowParams
     * @return
     */
    FlowParams fxsh(FlowParams flowParams);


    /**
     * 部门经理审核
     * @param flowParams
     * @return
     */
    FlowParams bmjlsh(FlowParams flowParams);


    /**
     * 签订合同
     * @param flowParams
     * @return
     */
    FlowParams qdht(FlowParams flowParams);


    /**
     * 总经理审批
     * @param flowParams
     * @return
     */
    FlowParams zjlsp(FlowParams flowParams);

    /**
     * 提交放款审批
     * @param flowParams
     * @return
     */
    FlowParams tjfksp(FlowParams flowParams);

}
