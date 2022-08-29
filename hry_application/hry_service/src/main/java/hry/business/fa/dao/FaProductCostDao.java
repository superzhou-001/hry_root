/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:33:52 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaProductCost;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FaProductCostDao </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:33:52 
 */
@Mapper
public interface FaProductCostDao extends BaseDao<FaProductCost, Long> {

}
