/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年9月21日 上午11:28:30
 */
package hry.platform.newuser.service.impl;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppRole;
import hry.platform.newuser.model.NewAppRoleMenu;
import hry.platform.newuser.model.NewAppUserRole;
import hry.platform.newuser.service.NewAppMenuService;
import hry.platform.newuser.service.NewAppRoleMenuService;
import hry.platform.newuser.service.NewAppRoleService;
import hry.platform.newuser.service.NewAppUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年9月21日 上午11:28:30
 */
@Service("newAppRoleService")
@Slf4j
public class NewAppRoleServiceImpl extends BaseServiceImpl<NewAppRole, Long> implements NewAppRoleService {

	@Resource(name= "newAppRoleDao")
	@Override
	public void setDao(BaseDao<NewAppRole, Long> dao) {
		super.dao = dao;
	}


	@Resource
	private NewAppUserRoleService appUserRoleService;

	@Resource
	private NewAppRoleMenuService appRoleMenuService;

	@Autowired
	private NewAppMenuService newAppMenuService;

	@Override
	public JsonResult add(NewAppRole appRole,String menuIds) {
		JsonResult jsonResult = new JsonResult();
		//判空
		if(StringUtils.isEmpty(menuIds)){
			jsonResult.setSuccess(false);
			jsonResult.setMsg("权限不能为空");
			return jsonResult;
		}else{
			//保存角色
			this.save(appRole);
			String[] ids = org.apache.commons.lang3.StringUtils.split(menuIds, ",");
			saveRoleAndMenu(appRole, ids);

			jsonResult.setSuccess(true);
			return jsonResult;
		}
	}

	/**
	 *  @author: liuchenghui
	 *  @Date: 2020/4/13 18:55
	 *  @Description: 保存菜单与角色的关系
	 */
	private void saveRoleAndMenu (NewAppRole appRole, String[] ids) {
		for (String id : ids) {
			// 查询菜单
			NewAppMenu appMenu = newAppMenuService.get(new Long(id));
			if (appMenu != null) {
				// 校验new_app_role_menu表中是否存在该节点
				// 因vue 当子节点全选时会传入父节点信息
				QueryFilter roleMenuFilter = new QueryFilter(NewAppRoleMenu.class);
				roleMenuFilter.addFilter("roleid= ", id);
				roleMenuFilter.addFilter("menukey=", appMenu.getMkey());
				NewAppRoleMenu roleMenu = appRoleMenuService.get(roleMenuFilter);
				if (roleMenu == null) {
					NewAppRoleMenu appRoleMenuTree = new NewAppRoleMenu();
					appRoleMenuTree.setRoleid(appRole.getId());
					appRoleMenuTree.setMenukey(appMenu.getMkey());
					appRoleMenuService.save(appRoleMenuTree);
					//存入当前菜单的所有父级菜单
					saveParentMenu(appMenu.getPkey(), appRole.getId());
				}
			}
		}
	}
	/**
	 * 存入当前菜单的父级菜单
	 */
	private void saveParentMenu (String pkey, Long roleId) {
		QueryFilter filter = new QueryFilter(NewAppMenu.class);
		filter.addFilter("mkey=", pkey);
		NewAppMenu appMenu = newAppMenuService.get(filter);
		if (appMenu != null) {
			if (!"root".equals(appMenu.getPkey())) {
				saveParentMenu(appMenu.getPkey(), roleId);
			}
			// 校验new_app_role_menu表中是否存在该父节点信息
			QueryFilter roleMenuFilter = new QueryFilter(NewAppRoleMenu.class);
			roleMenuFilter.addFilter("roleid= ", roleId);
			roleMenuFilter.addFilter("menukey=", appMenu.getMkey());
			NewAppRoleMenu roleMenu = appRoleMenuService.get(roleMenuFilter);
			if (roleMenu == null) {
				// 为空则保存
				NewAppRoleMenu appRoleMenuTree = new NewAppRoleMenu();
				appRoleMenuTree.setRoleid(roleId);
				appRoleMenuTree.setMenukey(appMenu.getMkey());
				appRoleMenuService.save(appRoleMenuTree);
			}
		}
	}


	@Override
	public JsonResult remove(String id) {
		JsonResult jsonResult = new JsonResult();
		//判空
		if(StringUtils.isEmpty(id)){
			jsonResult.setSuccess(false);
			jsonResult.setMsg("id不能为空");
			return jsonResult;
		}else{
			//保存关联信息
			NewAppRole appRole = this.get(Long.valueOf(id));

			QueryFilter filter = new QueryFilter(NewAppUserRole.class);
			filter.addFilter("roleid=", id);
			List<NewAppUserRole> findByAppRole = appUserRoleService.find(filter);

			if(findByAppRole!=null&&findByAppRole.size()>0){
				jsonResult.setSuccess(false);
				jsonResult.setMsg("有用户关联此角色，请先解除关联");
				return jsonResult;
			}
			//删除角色对应的权限
			QueryFilter queryFilter = new QueryFilter(NewAppRoleMenu.class);
			queryFilter.addFilter("roleid=", appRole.getId());
			appRoleMenuService.delete(queryFilter);
			//删除角色
			dao.delete(appRole);
			jsonResult.setSuccess(true);
			return jsonResult;
		}

	}

	@Override
	public JsonResult modify(HttpServletRequest request, NewAppRole appRole) {
		String oauthids = request.getParameter("oauthids");
		JsonResult jsonResult = new JsonResult();
		//判空
		if(StringUtils.isEmpty(oauthids)){
			jsonResult.setSuccess(false);
			jsonResult.setMsg("权限不能为空");
			return jsonResult;
		}else{

			NewAppRole _appRole = this.get(appRole.getId());
			_appRole.setName(appRole.getName());
			_appRole.setType(appRole.getType());
			_appRole.setRemark(appRole.getRemark());
			//修改角色
			this.update(_appRole);
			//删除所有的关联关系
			QueryFilter queryFilter = new QueryFilter(NewAppRoleMenu.class);
			queryFilter.addFilter("roleid=", appRole.getId());
			appRoleMenuService.delete(queryFilter);
			//保存新的关联关系
			String[] ids = org.apache.commons.lang3.StringUtils.split(oauthids, ",");
			saveRoleAndMenu(appRole, ids);

			jsonResult.setSuccess(true);
			return jsonResult;
		}

	}

	@Override
	public JsonResult testAdd(HttpServletRequest request) {
		return null;
	}

	@Override
	public JsonResult loadmenubyroleid(Long roleid) {
		JsonResult jsonResult = new JsonResult();
		// 最底层子节点
		List<Long> ids = new ArrayList<>();
		// 分类集合
		Map<String, List<Long>> map = new HashMap<>();
		// 获取角色对应的菜单
		List<NewAppMenu> find = appRoleMenuService.loadmenubyroleid(roleid);
		if (find != null && find.size() > 0) {
			// 筛选出一级父
			List<NewAppMenu> oneMenu = new ArrayList<>();
			find.forEach(menu -> {
				if ("root".equals(menu.getPkey())) {
					oneMenu.add(menu);
				}
			});
			// 分类
			oneMenu.forEach(menu -> {
				List<Long> menuIdList = new ArrayList<>();
				List<NewAppMenu> children = new ArrayList<>();
				children(menu.getMkey(), find, children);
				children.forEach(ch -> menuIdList.add(ch.getId()));
				map.put(menu.getMkey(), menuIdList);
			});
			find.stream().forEach(appMenu -> {
				// 排除有子节点的菜单，vue回显如果存在父节点，则会把子节点全选，导致回显错误，去除父节点，让其反选
				List<NewAppMenu> menus = newAppMenuService.find(new QueryFilter(NewAppMenu.class).addFilter("pkey=", appMenu.getMkey()));
				if (menus == null || menus.size() == 0) {
					ids.add(appMenu.getId());
				}
			});
			map.put("floorChildren", ids);
			return jsonResult.setSuccess(true).setObj(map);
		}
		return jsonResult.setSuccess(false).setMsg("请为角色分配菜单");
	}

    /*
    * 对查出来的菜单集合进行分类
    * */
	private void children(String mkey, List<NewAppMenu> allMenu, List<NewAppMenu> children) {
		List<NewAppMenu> menuList = new ArrayList<>();
		allMenu.forEach(menu -> {
			if (mkey.equals(menu.getPkey())) {
				menuList.add(menu);
			}
		});
		if (menuList.size() > 0) {
			children.addAll(menuList);
			menuList.forEach(menu -> {
				children(menu.getMkey(), allMenu, children);
			});
		}
	}

}
