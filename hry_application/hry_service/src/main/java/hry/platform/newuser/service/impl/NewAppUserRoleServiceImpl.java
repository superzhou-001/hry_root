/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午7:27:20
 */
package hry.platform.newuser.service.impl;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.newuser.dao.NewAppUserRoleDao;
import hry.platform.newuser.model.NewAppUserRole;
import hry.platform.newuser.service.NewAppUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月9日 下午7:27:20
 */
@Service("newAppUserRoleService")
public class NewAppUserRoleServiceImpl extends BaseServiceImpl<NewAppUserRole, Long> implements NewAppUserRoleService {

	@Resource(name = "newAppUserRoleDao")
	@Override
	public void setDao(BaseDao<NewAppUserRole, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult findUserRole(String userid) {
		List<NewAppUserRole> userRoleList = ((NewAppUserRoleDao)dao).findUserRoleList(userid);
		return new JsonResult(true).setObj(userRoleList);
	}
}

