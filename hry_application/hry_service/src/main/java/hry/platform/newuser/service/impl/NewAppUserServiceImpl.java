/**
 * Copyright:   互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-05-27 16:05:55
 */
package hry.platform.newuser.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.dao.NewAppUserDao;
import hry.platform.newuser.model.*;
import hry.platform.newuser.service.*;
import hry.redis.RedisService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.JWTUtil;
import hry.util.PasswordHelper;
import hry.util.StringUtil;
import hry.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p> AppUserService </p>
 * @author:         liushilei
 * @Date :          2017-05-27 16:05:55
 */
@Service("newAppUserService")
public class NewAppUserServiceImpl extends BaseServiceImpl<NewAppUser, Long> implements NewAppUserService {

	@Resource(name="newAppUserDao")
	@Override
	public void setDao(BaseDao<NewAppUser, Long> dao) {
		super.dao = dao;
	}

	@Resource
	private NewAppOrganizationService appOrganizationService;
	@Resource
	private NewAppUserOrganizationService appUserOrganizationService;
	@Resource
	private NewAppUserRoleService appUserRoleService;
	@Resource
	private NewAppRoleMenuService appRoleMenuService;
	@Resource
	private NewAppMenuService appMenuService;
	@Resource
	private NewAppUserPostService newAppUserPostService;
	@Resource
	private NewAppUserGroupService newAppUserGroupService;
	@Resource
	private NewAppUserUpordownService newAppUserUpordownService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private AppLoginLogService appLoginLogService;


	@Override
	//查基库
	public NewAppUser checkUsername(String username) {
		QueryFilter queryFilter = new QueryFilter(NewAppUser.class);
		queryFilter.addFilter("userName=",username);
		return get(queryFilter);


	}

	@Override
	public PageResult findPageBySql(HttpServletRequest request) {
		/*去掉根据部门查询部门下所有用户*/
		/*String organizationid = request.getParameter("organizationid");
		if(!StringUtils.isEmpty(organizationid)){
			NewAppOrganization appOrganization = appOrganizationService.get(Long.valueOf(organizationid));
			//递归查找当前组织下的所有组织 返回 ids 1,2,3
			String ids = appOrganizationService.findSonsToIds(appOrganization);

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids.split(","));

			//封装必要参数
			QueryFilter filter = new QueryFilter(NewAppUser.class, request);
			//分页插件
			Page<NewAppUser> page = PageFactory.getPage(filter);

			//查询方法
			List list = ((NewAppUserDao)dao).findPageBySql(map);

			return new PageResult(page, filter.getPage(),filter.getPageSize());
		}*/
		// 查询所有用户
		QueryFilter filter = new QueryFilter(NewAppUser.class, request);
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		//分页插件
		Page<NewAppUser> page = PageFactory.getPage(filter);
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtil.isNull(name)) {
			map.put("name", name);
		}
		if (StringUtil.isNull(username)) {
			map.put("username", username);
		}
		// 获取admin 超级管理员
		String superAdmin = redisService.get("app.system.admin");
		map.put("notUserName", superAdmin); // 剔除超级管理员
		//查询方法
		List<NewAppUser> list = ((NewAppUserDao)dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(),filter.getPageSize());
	}

	@Override
	public JsonResult addUser(NewAppUser appUser) {
		QueryFilter filter = new QueryFilter(NewAppUser.class);
		filter.addFilter("username=", appUser.getUserName());
		NewAppUser _appuser = super.get(filter);
		if(_appuser!=null){
			return new JsonResult().setMsg("账号重复!");
		}
		//加密密码
		String[] encryptPassword = {"1","2"};
		String salt = UUIDUtil.getUUID();
		appUser.setSalt(salt);
		appUser.setPassWord(PasswordHelper.md5(appUser.getPassWord(),salt));
		//保存用户
		super.save(appUser);
		addUserInfo(appUser);
		return new JsonResult().setSuccess(true);
	}

	@Override
	public JsonResult modifyUser(NewAppUser appUser) {
		//修改用户
		super.update(appUser);
		// 删除用户相关联信息
		delUserInfo(appUser.getId());
		// 添加用户相关联信息
		addUserInfo(appUser);
		return new JsonResult().setSuccess(true);
	}


	@Override
	public JsonResult remove(String ids) {
		String[] idList = ids.split(",");
		Arrays.asList(idList).forEach((sid) -> {
			Long id = Long.parseLong(sid);
			// 删除用户
			super.delete(id);
			// 删除用户相关联信息
			delUserInfo(id);
			// 删除用户上下级关系
			delUpOrDown(id);
		});
		return new JsonResult().setSuccess(true);
	}

	/**
	 * 添加用户相关联信息
	 * */
	private JsonResult addUserInfo(NewAppUser appUser) {
		// 保存部门，一个用户仅只属于一个部门
		Long departmentId = appUser.getDepartmentNameId();
		if (departmentId != null) {
			NewAppOrganization department = appOrganizationService.get(departmentId);
			//保存部门
			NewAppUserOrganization appUserOrganization = new NewAppUserOrganization();
			appUserOrganization.setUserid(appUser.getId());
			appUserOrganization.setOrganizationid(department.getId());
			appUserOrganization.setType(department.getType());
			appUserOrganizationService.save(appUserOrganization);
		}

		// 保存岗位--一个用户可属于多个岗位
		String postids = appUser.getPostIds();
		if (StringUtil.isNull(postids)) {
			String[] postidList = postids.split(",");
			Arrays.asList(postidList).forEach((postid) -> {
				NewAppUserPost userPost = new NewAppUserPost();
				userPost.setUserid(appUser.getId());
				userPost.setPostid(Long.parseLong(postid));
				newAppUserPostService.save(userPost);
			});
		}

		// 保存角色--一个用户可拥有多个角色
		String roleids = appUser.getRoleIds();
		if (StringUtil.isNull(roleids)) {
			String[] roleidList = roleids.split(",");
			Arrays.asList(roleidList).forEach((roleid) -> {
				NewAppUserRole appUserRole = new NewAppUserRole();
				appUserRole.setUserid(appUser.getId());
				appUserRole.setRoleid(Long.parseLong(roleid));
				appUserRoleService.save(appUserRole);
			});
		}

		// 保存用户组--一个用户可属于多个用户组
		String groupids = appUser.getGroupIds();
		if (StringUtil.isNull(groupids)) {
			String[] groupidList = groupids.split(",");
			Arrays.asList(groupidList).forEach((groupid) ->{
				NewAppUserGroup userGroup = new NewAppUserGroup();
				userGroup.setUserid(appUser.getId());
				userGroup.setGroupid(Long.parseLong(groupid));
				newAppUserGroupService.save(userGroup);
			});
		}
		return new JsonResult(true);
	}

	/**
	 * 删除用户相关联信息
	 * */
	private JsonResult delUserInfo(Long userId) {
		// 删除用户部门关系
		appUserOrganizationService.delete(new QueryFilter(NewAppUserOrganization.class).addFilter("userid=", userId));
		// 删除用户岗位关系
		newAppUserPostService.delete(new QueryFilter(NewAppUserPost.class).addFilter("userid=", userId));
		// 删除用户对应角色关系
		appUserRoleService.delete(new QueryFilter(NewAppUserRole.class).addFilter("userid=", userId));
		// 删除用户对应用户组关系
		newAppUserGroupService.delete(new QueryFilter(NewAppUserGroup.class).addFilter("userid=", userId));
		return new JsonResult(true);
	}

	/**
	 * 清除上下级关系信息
	 * */
	private JsonResult delUpOrDown(Long userId) {
		newAppUserUpordownService.delete(new QueryFilter(NewAppUserUpordown.class).addFilter("userid=", userId));
		return new JsonResult(true);
	}

	@Override
	public Set<String> findUserShiroUrl(String username) {

		QueryFilter f = new QueryFilter(NewAppUser.class);
		f.addFilter("username=", username);
		NewAppUser appUser = get(f);
		Set<String> set = new HashSet<String>();
		if(appUser != null){
			// 获取admin 超级管理员
			String superAdmin = redisService.get("app.system.admin");
			// 判断是否为超级管理员
			if (superAdmin.equals(username)) {
				// 超级管理全拥有所有菜单及按钮权限
				List<NewAppMenu> menuList = appMenuService.findAll();
				menuList.forEach((menu) ->{
					String shirourl = menu.getShirourl();
					if (!StringUtils.isEmpty(shirourl)) {
						set.add(shirourl);
					}
				});
				return set;
			}
			// 查询账号对应的角色
			QueryFilter filter = new QueryFilter(NewAppUserRole.class);
			filter.addFilter("userid=", appUser.getId());
			List<NewAppUserRole> urList = appUserRoleService.find(filter);
			if (urList != null && urList.size() > 0) {
				urList.forEach((role) -> {
					// 查询角色对应的菜单权限
					QueryFilter filter2 = new QueryFilter(NewAppRoleMenu.class);
					filter2.addFilter("roleid=", role.getRoleid());
					List<NewAppRoleMenu> rmList = appRoleMenuService.find(filter2);
					if (rmList != null && rmList.size() > 0) {
						rmList.forEach((rm) ->{
							// 根据菜单权限key,查询对应的菜单
							NewAppMenu appMenu = appMenuService.get(new QueryFilter(NewAppMenu.class)
									.addFilter("mkey=", rm.getMenukey()));
							if (appMenu != null) {
								String shirourl = appMenu.getShirourl();
								if (!StringUtils.isEmpty(shirourl)) {
									set.add(shirourl);
								}
							}
						});
					}
				});
			}
		}
		return set;
	}

	@Override
	public JsonResult updateUserShiroUrl(HttpServletRequest request) {
		String token = request.getHeader("token");
		NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		//查询用户拥有的权限
		Set<String> userShiroUrl = this.findUserShiroUrl(user.getUserName());
		Object[] shiroUrls = userShiroUrl.toArray();
		// 将用户权限存储到redis中
		redisService.save(JWTUtil.getManageShiroUrlsKey(token), JSON.toJSONString(shiroUrls),JWTUtil.EXPIRE_TIME);
		return new JsonResult(true);
	}

	@Override
	public JsonResult resetpw(Long id, String password) {
		NewAppUser appUser = get(Long.valueOf(id));
		//加密密码
		appUser.setPassWord(PasswordHelper.md5(password,appUser.getSalt()));
		//保存用户
		super.update(appUser);
		return  new JsonResult().setSuccess(true);
	}

	@Override
	public PageResult findNotUserOrganList(QueryFilter filter) {
		Page<NewAppUser> page = PageFactory.getPage(filter);
		String name = filter.getRequest().getParameter("name");
		String userName = filter.getRequest().getParameter("userName");
		// 获取admin 超级管理员
		String superAdmin = redisService.get("app.system.admin");
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isNull(name)) {
			map.put("name", name + "%");
		}
		if (StringUtil.isNull(userName)) {
			map.put("userName", userName + "%");
		}
		map.put("notUserName", superAdmin); // 剔除超级管理员
		((NewAppUserDao)dao).findNotUserOrganList(map);
		return new PageResult(page, filter.getPage(),filter.getPageSize());
	}

	@Override
	public JsonResult getUser(Long id) {
		NewAppUser appUser = super.get(id);
		// 获取用户下部门id
		QueryFilter orgFilter = new QueryFilter(NewAppUserOrganization.class);
		orgFilter.addFilter("userid=", id);
		NewAppUserOrganization organization = appUserOrganizationService.get(orgFilter);
		appUser.setDepartmentNameId(organization != null? organization.getOrganizationid() : null);
		// 查询用户下岗位id
		QueryFilter postFilter = new QueryFilter(NewAppUserPost.class);
		postFilter.addFilter("userid=", id);
		List<NewAppUserPost> userPostList = newAppUserPostService.find(postFilter);
		String userPostIds = null;
		if (userPostList != null && userPostList.size() > 0)
			for (NewAppUserPost userPost : userPostList) {
				if (userPostIds == null) {
					userPostIds = userPost.getPostid().toString();
				} else {
					userPostIds += "," + userPost.getPostid().toString();
				}
			}
		appUser.setPostIds(userPostIds);
		// 查询用户下角色id
		QueryFilter roleFilter = new QueryFilter(NewAppUserRole.class);
		roleFilter.addFilter("userid=", id);
		List<NewAppUserRole> userRoleList = appUserRoleService.find(roleFilter);
		String userRoleIds = null;
		if (userRoleList != null && userRoleList.size() > 0)
			for (NewAppUserRole userRole : userRoleList) {
				if (userRoleIds == null) {
					userRoleIds = userRole.getRoleid().toString();
				} else {
					userRoleIds += "," + userRole.getRoleid().toString();
				}
			}
		appUser.setRoleIds(userRoleIds);
		// 查询用户下用户组
		QueryFilter groupFilter = new QueryFilter(NewAppUserGroup.class);
		groupFilter.addFilter("userid=", id);
		List<NewAppUserGroup> userGroupList = newAppUserGroupService.find(groupFilter);
		String userGroupIds = null;
		if (userGroupList != null && userGroupList.size() > 0)
			for (NewAppUserGroup userGroup : userGroupList) {
				if (userGroupIds == null) {
					userGroupIds = userGroup.getGroupid().toString();
				} else {
					userGroupIds += "," + userGroup.getGroupid().toString();
				}
			}
		appUser.setGroupIds(userGroupIds);
		// 获取admin 超级管理员
		String superAdmin = redisService.get("app.system.admin");
		if (superAdmin.equals(appUser.getUserName())) {
			appUser.setSuperAdmin(1);
		}
		// 获取最后登录时间
		QueryFilter filter = new QueryFilter(AppLoginLog.class);
		filter.addFilter("userId=", id);
		filter.setOrderby("id desc");
		AppLoginLog loginlog = appLoginLogService.get(filter);
		if (loginlog != null) {
			appUser.setLoginTime(loginlog.getLoginTime());
		}

		return new JsonResult(true).setObj(appUser);
	}

}
