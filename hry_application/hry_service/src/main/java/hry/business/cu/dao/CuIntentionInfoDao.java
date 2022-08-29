/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:37:17 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuIntentionInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CuIntentionInfoDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:37:17 
 */
@Mapper
public interface CuIntentionInfoDao extends BaseDao<CuIntentionInfo, Long> {

}
