/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:14:23 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AppStationContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppStationContentDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:14:23 
 */
@Mapper
public interface AppStationContentDao extends BaseDao<AppStationContent, Long> {

}
