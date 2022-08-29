/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 15:55:35 
 */
package hry.business.cf.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.business.cf.model.CfFacilityMortgage;
import hry.core.util.QueryFilter;

/**
 * <p> CfFacilityMortgageService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 15:55:35 
 */
public interface CfFacilityMortgageService extends BaseService<CfFacilityMortgage, Long> {

    /**
     * 根据条件查询信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);

    /**
     * 添加抵质押物信息
     * @param jsonStr
     * @return
     */
    JsonResult mortgageAdd(String jsonStr);

    /**
     * 查询抵质押物信息
     * @param id
     * @return
     */
    JsonResult getMortgage(Long id);

    /**
     * 修改抵质押物信息
     * @param jsonStr
     * @return
     */
    JsonResult updateMortgage(String jsonStr);

    /**
     * 删除抵质押物信息
     * @param id
     * @return
     */
    JsonResult removeMortgage(Long id);

}
