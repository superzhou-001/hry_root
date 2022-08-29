/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-02 14:52:53 
 */
package hry.business.cf.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.business.cf.model.CfFacilityGuarantee;
import hry.core.util.QueryFilter;

/**
 * <p> CfFacilityGuaranteeService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-02 14:52:53 
 */
public interface CfFacilityGuaranteeService extends BaseService<CfFacilityGuarantee, Long> {

    /**
     * 根据条件查询信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);

    /**
     * 查询信息
     * @param id
     * @return
     */
    JsonResult getGuarantee(Long id);

}
