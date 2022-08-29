/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:19:39 
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.platform.newuser.model.NewAppUserGroup;

import java.util.List;

/**
 * <p> NewAppUserGroupService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:19:39 
 */
public interface NewAppUserGroupService extends BaseService<NewAppUserGroup, Long> {
    List<NewAppUserGroup> findUserGroup(Long groupId);

    JsonResult findUserGroup(String userid);
}
