/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 16:17:01 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuHouse;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CuHouseDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 16:17:01 
 */
@Mapper
public interface CuHouseDao extends BaseDao<CuHouse, Long> {

}
