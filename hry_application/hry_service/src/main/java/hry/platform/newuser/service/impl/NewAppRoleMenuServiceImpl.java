/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午7:27:20
 */
package hry.platform.newuser.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.newuser.dao.NewAppRoleMenuDao;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppRoleMenu;
import hry.platform.newuser.service.NewAppRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月9日 下午7:27:20
 */
@Service("newAppRoleMenuService")
public class NewAppRoleMenuServiceImpl extends BaseServiceImpl<NewAppRoleMenu, Long>  implements NewAppRoleMenuService {

	@Resource(name = "newAppRoleMenuDao")
	@Override
	public void setDao(BaseDao<NewAppRoleMenu, Long> dao) {
		super.dao = dao;
	}

	/**
	 * 根据用户id获取其对应的菜单角色
	 *
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public List<String> findUserMenuRoleByuserId (String userId) {
		Map<String, Object> param = new HashMap<>();
		if (!StringUtils.isEmpty(userId)) {
			param.put("userId", userId);
		}
		return ((NewAppRoleMenuDao)dao).findUserMenuRoleByuserId(param);
	}

	@Override
	public List<NewAppMenu> findMenuRoleByuserId (Object userId) {
		Map<String, Object> param = new HashMap<>();
		if (!StringUtils.isEmpty(userId)) {
			param.put("userId", userId);
		}
		return ((NewAppRoleMenuDao)dao).findMenuRoleByuserId(param);
	}

	@Override
	public List<NewAppMenu> loadmenubyroleid (Long roleid) {
		Map<String, Object> param = new HashMap<>();
		param.put("roleid", roleid);
		return ((NewAppRoleMenuDao)dao).loadmenubyroleid(param);
	}
}

