/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:01:27 
 */
package hry.scm.project.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.scm.project.model.MortgageTotal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p> MortgageTotalService </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:01:27 
 */
public interface MortgageTotalService extends BaseService<MortgageTotal, Long> {

    /**
     * 实时查询某企业库存统计数据
     * @param map
     * @return
     */
    public List<MortgageTotal> findTotalBySql(Map<String,String> map);

    /**
     * 查询某种质押物的具体库存信息
     * @param map
     * @return
     */
    public JsonResult findStorage(Map<String,String> map);

    /**
     * 现货质押价格管理
     * @param filter
     * @return
     */
    public PageResult list(QueryFilter filter);

    /**
     * 查询质押业务总价值
     * @param map
     * @return
     */
    public BigDecimal findMortWeight(Map<String, String> map);

    /**
     * 查询某种质押物的具体质押库存信息
     * @param map
     * @return
     */
    public JsonResult findDetail(Map<String,String> map);

}
