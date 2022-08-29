/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 09:52:22 
 */
package hry.business.fa.dao;

import hry.business.fa.model.FaFactoringCost;
import hry.core.mvc.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> FaFactoringCostDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 09:52:22 
 */
@Mapper
public interface FaFactoringCostDao extends BaseDao<FaFactoringCost, Long> {
    /**
     * 新sql分页查询
     * @param map
     * @return
     */
    List<FaFactoringCost> findPageBySql(Map<String,Object> map);

}
