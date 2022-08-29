/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-31 16:04:36
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppMenuTop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p> NewAppMenuTopDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-31 16:04:36
 */
@Mapper
public interface NewAppMenuTopDao extends BaseDao<NewAppMenuTop, Long> {

    Integer getMaxOrderNo (@Param(value="pkey") String pkey);
}
