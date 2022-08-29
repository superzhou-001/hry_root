/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-22 13:47:08 
 */
package hry.business.fa.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaFundIntent;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;

import java.util.Map;

/**
 * <p> FaFundIntentService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-22 13:47:08 
 */
public interface FaFundIntentService extends BaseService<FaFundIntent, Long> {

    /**
     * 分页查询确认还款计划
     * */
    public PageResult findPageFundIntent(QueryFilter filter);

    /**
     * 修改还款计划状态
     * */
    public JsonResult updateStatus(Map<String, String> param);

    /**
     * 还款计划入库
     * */
    public JsonResult saveFundList(Long factoringId, String faFundListJson);


}
