/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:36:21 
 */
package hry.business.cu.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.business.cu.model.CuIntention;
import hry.core.util.QueryFilter;

/**
 * <p> CuIntentionService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:36:21 
 */
public interface CuIntentionService extends BaseService<CuIntention, Long> {

    /**
     * 根据条件查询企业信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);

    /**
     * @Author: yaozh
     * @Description: 转为正式客户
     * @param null
     * @Date: 2020/5/19 14:48
     */
    JsonResult updateStatusFormal(Long id);

    /**
     * 添加意向客户信息
     * @param cuIntention
     * @return
     */
    JsonResult addIntention(CuIntention cuIntention);

}
