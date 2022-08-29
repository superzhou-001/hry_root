/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-17 15:08:55 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.Price;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> PriceDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-17 15:08:55 
 */
@Mapper
public interface PriceDao extends BaseDao<Price, Long> {

}
