/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:26:51 
 */
package hry.platform.config.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.config.model.AppHolidaysInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppHolidaysInfoDao </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:26:51 
 */
@Mapper
public interface AppHolidaysInfoDao extends BaseDao<AppHolidaysInfo, Long> {

}
