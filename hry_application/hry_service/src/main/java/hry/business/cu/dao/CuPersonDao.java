/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:29:14 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuPerson;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CuPersonDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:29:14 
 */
@Mapper
public interface CuPersonDao extends BaseDao<CuPerson, Long> {

}
