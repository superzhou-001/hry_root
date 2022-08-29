/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-13 14:57:20 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AppUserStation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> AppUserStationDao </p>
 *
 * @author: zhouming
 * @Date: 2020-08-13 14:57:20 
 */
@Mapper
public interface AppUserStationDao extends BaseDao<AppUserStation, Long> {

    List<AppUserStation> findUserStationList(Map<String, Object> map);
}
