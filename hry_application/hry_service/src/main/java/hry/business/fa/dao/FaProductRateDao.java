/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:32:05 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaProductRate;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FaProductRateDao </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:32:05 
 */
@Mapper
public interface FaProductRateDao extends BaseDao<FaProductRate, Long> {

}
