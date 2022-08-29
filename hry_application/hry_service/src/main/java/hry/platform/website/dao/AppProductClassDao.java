/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:38:17 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AppProductClass;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppProductClassDao </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:38:17 
 */
@Mapper
public interface AppProductClassDao extends BaseDao<AppProductClass, Long> {

}
