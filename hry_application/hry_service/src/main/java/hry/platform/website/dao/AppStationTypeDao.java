/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:13:14 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AppStationType;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppStationTypeDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:13:14 
 */
@Mapper
public interface AppStationTypeDao extends BaseDao<AppStationType, Long> {

}
