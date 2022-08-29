/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:19:39 
 */
package hry.platform.newuser.service.impl;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.newuser.dao.NewAppUserGroupDao;
import hry.platform.newuser.model.NewAppUserGroup;
import hry.platform.newuser.service.NewAppUserGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> NewAppUserGroupService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:19:39 
 */
@Service("newAppUserGroupService")
public class NewAppUserGroupServiceImpl extends BaseServiceImpl<NewAppUserGroup, Long> implements NewAppUserGroupService {

	@Resource(name = "newAppUserGroupDao")
	@Override
	public void setDao (BaseDao<NewAppUserGroup, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<NewAppUserGroup> findUserGroup(Long groupId) {
		return ((NewAppUserGroupDao)dao).findUserGroup(groupId);
	}

	@Override
	public JsonResult findUserGroup(String userid) {
		List<NewAppUserGroup> userGroupList = ((NewAppUserGroupDao)dao).findUserGroupList(userid);
		return new JsonResult(true).setObj(userGroupList);
	}
}
