/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年9月21日 上午11:27:58
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.platform.newuser.model.NewAppRole;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年9月21日 上午11:27:58
 */
public interface NewAppRoleService extends BaseService<NewAppRole, Long>{

	/**
	 * <p> TODO</p>  添加角色
	 * @author:         Liu Shilei
	 * @param:    @param request
	 * @param:    @param appRole
	 * @param:    @return
	 * @return: JsonResult
	 * @Date :          2015年12月10日 下午1:30:46
	 * @throws:
	 */
	JsonResult add( NewAppRole appRole,String menuIds);

	/**
	 * <p> TODO</p>删除角色---同时删除角色下对应的权限
	 * @author:         Liu Shilei
	 * @param:    @param id
	 * @param:    @return
	 * @return: JsonResult
	 * @Date :          2015年12月10日 下午1:52:36
	 * @throws:
	 */
	JsonResult remove(String ids);



	/**
	 * <p> TODO</p> 修改角色---更新角色权限关联表
	 * @author:         Liu Shilei
	 * @param:    @param request
	 * @param:    @param appRole
	 * @param:    @return
	 * @return: JsonResult
	 * @Date :          2015年12月10日 下午4:36:17
	 * @throws:
	 */
	JsonResult modify(HttpServletRequest request, NewAppRole appRole);

	/**
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param request
	 * @return: void
	 * @Date :          2016年2月18日 下午6:59:25
	 * @throws:
	 */
	JsonResult testAdd(HttpServletRequest request);

	/**
	 * 查看角色下的所有权限,权限按应用区分
	 * @param roleid
	 * @return
	 */
	JsonResult loadmenubyroleid(Long roleid);

}
