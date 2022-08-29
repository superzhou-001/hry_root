/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:29:53 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaProduct;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FaProductDao </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:29:53 
 */
@Mapper
public interface FaProductDao extends BaseDao<FaProduct, Long> {

}
