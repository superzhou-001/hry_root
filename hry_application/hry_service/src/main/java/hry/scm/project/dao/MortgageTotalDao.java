/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:01:27 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.MortgageTotal;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p> MortgageTotalDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:01:27 
 */
@Mapper
public interface MortgageTotalDao extends BaseDao<MortgageTotal, Long> {
    /**
     * 实时查询某企业库存统计数据
     * @param map
     * @return
     */
    public List<MortgageTotal> findTotalBySql(Map<String,String> map);

    /**
     * 查询质押业务总价值
     * @param map
     * @return
     */
    public BigDecimal findMortWeight(Map<String, String> map);

}
