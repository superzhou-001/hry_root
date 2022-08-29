/**
 * Copyright:    互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-06-01 19:44:41
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppMenu;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p> AppMenuDao </p>
 * @author:         liushilei
 * @Date :          2017-06-01 19:44:41
 */
@Mapper
public interface NewAppMenuDao extends BaseDao<NewAppMenu, Long> {

}
