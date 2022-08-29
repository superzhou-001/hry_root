/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-22 17:18:30 
 */
package hry.scm.project.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.scm.project.model.RedeemTotal;
import hry.scm.project.model.vo.RedeemTotalVo;

import java.util.List;
import java.util.Map;

/**
 * <p> RedeemTotalService </p>
 *
 * @author: luyue
 * @Date: 2020-07-22 17:18:30 
 */
public interface RedeemTotalService extends BaseService<RedeemTotal, Long> {
    /**
     * 解除质押物-查询质押物清单方法
     * @param map
     * @return
     */
    public JsonResult findDetail(Map<String,String> map);

    /**
     * 解除质押物-确认解除货品抵押
     * @param map
     * @return
     */
    public JsonResult confirmRelieve(Map<String,String> map);

    /**
     *赎货汇总清单查询
     * @param map
     * @return
     */
    public List<RedeemTotalVo> findRedeemTotalBySql(Map<String,String> map);



}
