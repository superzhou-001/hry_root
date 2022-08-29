/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-28 16:12:18 
 */
package hry.platform.config.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.config.model.YzyConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> YzyConfigDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-28 16:12:18 
 */
@Mapper
public interface YzyConfigDao extends BaseDao<YzyConfig, Long> {

}
