/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:24:37 
 */
package hry.platform.config.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.config.model.AppHolidaysYear;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppHolidaysYearDao </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:24:37 
 */
@Mapper
public interface AppHolidaysYearDao extends BaseDao<AppHolidaysYear, Long> {

}
