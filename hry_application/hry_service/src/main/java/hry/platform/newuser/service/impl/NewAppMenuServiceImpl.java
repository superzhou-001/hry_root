/**
 * Copyright:   互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-06-01 19:44:41
 */
package hry.platform.newuser.service.impl;

import com.alibaba.fastjson.JSON;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppMenuTop;
import hry.platform.newuser.model.NewAppRoleMenu;
import hry.platform.newuser.service.NewAppMenuService;
import hry.platform.newuser.service.NewAppMenuTopService;
import hry.platform.newuser.service.NewAppRoleMenuService;
import hry.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> AppMenuService </p>
 * @author:         liushilei
 * @Date :          2017-06-01 19:44:41
 */
@Service("newAppMenuService")
public class NewAppMenuServiceImpl extends BaseServiceImpl<NewAppMenu, Long> implements NewAppMenuService {

	@Resource(name="newAppMenuDao")
	@Override
	public void setDao(BaseDao<NewAppMenu, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private NewAppMenuTopService newAppMenuTopService;
	@Autowired
	private NewAppRoleMenuService newAppRoleMenuService;

	@Override
	public List<NewAppMenu> listTree(HttpServletRequest request) {

		QueryFilter filter = new QueryFilter(NewAppMenu.class);
		filter.addFilter("pkey_notlike", "%\\_%");
		List<NewAppMenu> find = find(filter);
		for(NewAppMenu appMenu : find){
			recursivefind(appMenu);
		}

		return find;

	}

	@Override
	public List<NewAppMenu> findTree(String appName, String shiroUrl) {
		// 获取所有菜单
		QueryFilter filter = new QueryFilter(NewAppMenu.class);
		if (shiroUrl != null) {
			filter.addFilter("type=", "menu");
		}
		filter.setOrderby("orderno asc");
		List<NewAppMenu> menuList = super.find(filter);
		if (menuList != null && menuList.size() > 0) {
			// 菜单进行分组
			Map<String, List<NewAppMenu>> listMap = menuList.stream().collect(Collectors.groupingBy(NewAppMenu::getPkey));
			// 获取根节点数据
			List<NewAppMenu> rootMenu = listMap.get("root");
			if (rootMenu != null && rootMenu.size() > 0) {
				// 过滤符合条件的 菜单
				List<NewAppMenu> appMenuStream = rootMenu.stream().filter(newAppMenu -> appName.equals(newAppMenu.getAppname())).collect(Collectors.toList());
				if (appMenuStream != null && appMenuStream.size() > 0) {
					// 根据菜单key获取数据
					List<NewAppMenu> menus = listMap.get(appName);
					if (menus != null && menus.size() > 0) {
						getRoleMenuTree(menus, listMap, shiroUrl);
					}
					appMenuStream.get(0).setChildren(menus);
				}
				return appMenuStream;
			}
		}
		return null;
	}

	/**
	 * 递归转载指定模块树结构
	 * */
	private void getRoleMenuTree (List<NewAppMenu> Menu, Map<String, List<NewAppMenu>> listMap, String shiroUrl) {
		if (Menu != null && Menu.size() > 0) {
			if (shiroUrl != null) {
				// 删除没有权限子菜单
				Menu.removeIf((menu) -> shiroUrl.indexOf(menu.getShirourl()) == -1);
			}
			Menu.stream().forEach(appMenu -> {
				List<NewAppMenu> childMenu = listMap.get(appMenu.getMkey());
				appMenu.setChildren(childMenu);
				getRoleMenuTree(childMenu, listMap, shiroUrl);
			});
		}
	}

	@Override
	public void initMenu() {
		// 查询顶部子菜单
		QueryFilter queryFilter = new QueryFilter(NewAppMenuTop.class);
		queryFilter.addFilter("pkey!=","root");
		List<NewAppMenuTop> list = newAppMenuTopService.find(queryFilter);
		if (list != null && list.size() > 0) {
			list.stream().forEach(topMenu -> syncMenu(topMenu));
		}
	}

	/**
	 *  @author: liuchenghui
	 *  @Date: 2020/4/8 17:25
	 *  @Description: 添加修改顶部菜单时，顶部子菜单同步到菜单中
	 */
	@Override
	public void syncMenu (NewAppMenuTop newAppMenuTop) {
		// 查询菜单中是否存在
		QueryFilter queryFilter = new QueryFilter(NewAppMenu.class);
		queryFilter.addFilter("appname=", newAppMenuTop.getMkey());
		queryFilter.addFilter("mkey=", newAppMenuTop.getMkey());
		NewAppMenu cfNewAppMenu = super.get(queryFilter);
		// 不存在保存，存在，更新
		if (cfNewAppMenu == null) {
			// 保存到菜单表中
			cfNewAppMenu = new NewAppMenu();
			cfNewAppMenu.setShirourl("/" + newAppMenuTop.getMkey());
			cfNewAppMenu.setAppname(newAppMenuTop.getMkey());
			cfNewAppMenu.setMkey(newAppMenuTop.getMkey());
			cfNewAppMenu.setOrderno(0);
			cfNewAppMenu.setIcon(newAppMenuTop.getIcon());
			cfNewAppMenu.setType("menu");
			cfNewAppMenu.setPkey("root");
			cfNewAppMenu.setName(newAppMenuTop.getName());
			super.save(cfNewAppMenu);
		} else {
			cfNewAppMenu.setShirourl("/" + newAppMenuTop.getMkey());
			cfNewAppMenu.setAppname(newAppMenuTop.getMkey());
			cfNewAppMenu.setMkey(newAppMenuTop.getMkey());
			cfNewAppMenu.setOrderno(0);
			cfNewAppMenu.setIcon(newAppMenuTop.getIcon());
			cfNewAppMenu.setType("menu");
			cfNewAppMenu.setName(newAppMenuTop.getName());
			super.update(cfNewAppMenu);
		}
	}

	@Override
	public String[] cascadeDeleteMenu (NewAppMenu appMenu) {
		String[] rt = new String[2];
		rt[0] = "0";
		rt[1] = "删除失败";

		// 查询该菜单下的所有子菜单，所有菜单与角色关系
		List<Long> menuIds = new ArrayList<>();
		List<Long> roleMenuIds = new ArrayList<>();

		// 获取子集菜单id
		menuIds.add(appMenu.getId());
		getChildMenuIds(appMenu, menuIds);

		// 根据子集菜单id，获取菜单与角色关系id
		if (menuIds != null && menuIds.size() > 0) {
			menuIds.stream().forEach(mid -> {
				NewAppMenu menu = get(mid);
				QueryFilter role_qf = new QueryFilter(NewAppRoleMenu.class);
				role_qf.addFilter("menukey=", menu.getMkey());
				List<NewAppRoleMenu> roleMenus = newAppRoleMenuService.find(role_qf);
				if (roleMenus != null && roleMenus.size() > 0) {
					roleMenus.stream().forEach(roleMenu -> roleMenuIds.add(roleMenu.getId()));
				}
			});

			boolean b = deleteBatch(menuIds);
			if (b) {
				boolean batch = newAppRoleMenuService.deleteBatch(roleMenuIds);
				if (batch) {
					rt[0] = "1";
					rt[1] = "删除成功";
				} else {
					rt[0] = "0";
					rt[1] = "菜单与角色关系删除失败";
				}
			} else {
				rt[0] = "0";
				rt[1] = "菜单删除失败";
			}
		}
		return rt;
	}

	/**
	 *  @author: liuchenghui
	 *  @Date: 2020/4/15 14:31
	 *  @Description: 获取子集菜单id
	 */
	private void getChildMenuIds (NewAppMenu appMenu, List<Long> menuIds) {
		QueryFilter menu_qf = new QueryFilter(NewAppMenu.class);
		menu_qf.addFilter("pkey=", appMenu.getMkey());
		List<NewAppMenu> appMenus = find(menu_qf);
		if (appMenus != null && appMenus.size() > 0) {
			appMenus.stream().forEach(menu -> {
				menuIds.add(menu.getId());
				getChildMenuIds(menu, menuIds);
			});
		}
	}

	/**
	 * 递归查询
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param appMenu
	 * @return: void
	 * @Date :          2016年5月26日 下午2:51:55
	 * @throws:
	 */
	public void recursivefind(NewAppMenu appMenu){

		if(appMenu!=null){
			QueryFilter queryFilter = new QueryFilter(NewAppMenu.class);
			queryFilter.addFilter("pkey=",appMenu.getMkey());
			queryFilter.setOrderby("orderno");
			List<NewAppMenu> list = find(queryFilter);
			appMenu.setChildren(list);
			for(NewAppMenu menu : list){
				recursivefind(menu);
			}
		}

	}


}
