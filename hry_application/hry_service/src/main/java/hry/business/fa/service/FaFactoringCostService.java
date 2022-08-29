/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 09:52:22 
 */
package hry.business.fa.service;

import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.business.fa.model.FaFactoringCost;
import hry.core.util.QueryFilter;

/**
 * <p> FaFactoringCostService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 09:52:22 
 */
public interface FaFactoringCostService extends BaseService<FaFactoringCost, Long> {

    /**
     * 根据条件查询企业信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);

}
