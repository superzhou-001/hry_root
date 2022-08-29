/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:18:03 
 */
package hry.platform.newuser.service.impl;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppGroup;
import hry.platform.newuser.model.NewAppUserGroup;
import hry.platform.newuser.service.NewAppGroupService;
import hry.platform.newuser.service.NewAppUserGroupService;
import hry.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.Action;
import java.util.Arrays;

/**
 * <p> NewAppGroupService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:18:03 
 */
@Service("newAppGroupService")
public class NewAppGroupServiceImpl extends BaseServiceImpl<NewAppGroup, Long> implements NewAppGroupService {

	@Autowired
	private NewAppUserGroupService newAppUserGroupService;
	@Resource(name = "newAppGroupDao")
	@Override
	public void setDao (BaseDao<NewAppGroup, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult addGroup(NewAppGroup newAppGroup) {
		Long groupId = newAppGroup.getId();
		if (groupId == null) {
			super.save(newAppGroup);
		} else {
			super.update(newAppGroup);
		}
		// 是否有用户加入组
		if (StringUtil.isNull(newAppGroup.getUserIds())) {
			// 删除该用户组下用户
			if (groupId != null) {
				QueryFilter filter = new QueryFilter(NewAppUserGroup.class);
				filter.addFilter("groupid=", groupId);
				newAppUserGroupService.delete(filter);
			}
			// 用户组添加用户
			String[] userIdList = newAppGroup.getUserIds().split(",");
			Arrays.asList(userIdList).forEach((userId) ->{
				NewAppUserGroup userGroup = new NewAppUserGroup();
				userGroup.setGroupid(newAppGroup.getId());
				userGroup.setUserid(Long.parseLong(userId));
				newAppUserGroupService.save(userGroup);
			});
		}
		return new JsonResult(true);
	}
}
