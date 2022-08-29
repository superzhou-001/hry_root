/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-13 16:04:23 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuIntentionUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CuIntentionUserDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-13 16:04:23 
 */
@Mapper
public interface CuIntentionUserDao extends BaseDao<CuIntentionUser, Long> {

}
