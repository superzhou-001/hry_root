/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-26 10:19:39 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuUpdateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CuUpdateLogDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-26 10:19:39 
 */
@Mapper
public interface CuUpdateLogDao extends BaseDao<CuUpdateLog, Long> {

}
