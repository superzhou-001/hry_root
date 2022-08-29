/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:27:30 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaProductType;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FaProductTypeDao </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:27:30 
 */
@Mapper
public interface FaProductTypeDao extends BaseDao<FaProductType, Long> {

}
