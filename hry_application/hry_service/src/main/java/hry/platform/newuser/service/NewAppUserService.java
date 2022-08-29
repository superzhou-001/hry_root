/**
 * Copyright:   互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-05-27 16:05:55
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;


/**
 * <p> AppUserService </p>
 * @author:         liushilei
 * @Date :          2017-05-27 16:05:55
 */
public interface NewAppUserService extends BaseService<NewAppUser, Long>{

	/**
	 * 从基库验证username是否存在
	 * @param username
	 * @return
	 */
	NewAppUser checkUsername(String username);

	/**
	 * sql分页
	 * @param request
	 * @return
	 */
	PageResult findPageBySql(HttpServletRequest request);

	/**
	 * 添加后台用户,同时后台用户保存到基础库中
	 * @param appUser
	 * @return
	 */
	JsonResult addUser(NewAppUser appUser);


	/**
	 * 查询所有权限
	 * @param userid
	 * @return
	 */
	Set<String> findUserShiroUrl(String username);

	/**
	 * 更新用户权限
	 * */
	JsonResult updateUserShiroUrl(HttpServletRequest request);

	/**
	 * 获取用户信息
	 * */
	JsonResult getUser(Long id);

	/**
	 * 修改后台用户
	 * @param appUser
	 * @return
	 */
	JsonResult modifyUser(NewAppUser appUser);

	/**
	 * 删除后台用户，同时删除基库中的appuser
	 * @param ids
	 * @return
	 */
	JsonResult remove(String ids);


	/**
	 * 重置密码
	 * @param id
	 * @param password
	 * @return
	 */
    JsonResult resetpw(Long id, String password);

    /***
	 * 查询未加入组织人员
	 * */
    PageResult findNotUserOrganList(QueryFilter filter);

}
