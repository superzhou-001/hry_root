/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:19:39 
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppUserGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p> NewAppUserGroupDao </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:19:39 
 */
@Mapper
public interface NewAppUserGroupDao extends BaseDao<NewAppUserGroup, Long> {
    List<NewAppUserGroup> findUserGroup(Long groupId);

    List<NewAppUserGroup> findUserGroupList(String userid);
}
