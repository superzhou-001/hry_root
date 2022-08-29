/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 16:45:28 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuCar;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CuCarDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 16:45:28 
 */
@Mapper
public interface CuCarDao extends BaseDao<CuCar, Long> {

}
