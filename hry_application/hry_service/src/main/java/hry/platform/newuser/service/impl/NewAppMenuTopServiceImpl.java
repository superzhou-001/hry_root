/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-31 16:04:36
 */
package hry.platform.newuser.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.newuser.dao.NewAppMenuTopDao;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppMenuTop;
import hry.platform.newuser.model.NewAppRoleMenu;
import hry.platform.newuser.service.NewAppMenuService;
import hry.platform.newuser.service.NewAppMenuTopService;
import hry.platform.newuser.service.NewAppRoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p> NewAppMenuTopService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-31 16:04:36
 */
@Service("newAppMenuTopService")
public class NewAppMenuTopServiceImpl extends BaseServiceImpl<NewAppMenuTop, Long> implements NewAppMenuTopService {

	@Resource(name = "newAppMenuTopDao")
	@Override
	public void setDao (BaseDao<NewAppMenuTop, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private NewAppMenuService newAppMenuService;
	@Autowired
	private NewAppRoleMenuService newAppRoleMenuService;

	@Override
	public Integer getMaxOrderNo (String pkey) {
		return ((NewAppMenuTopDao)dao).getMaxOrderNo(pkey);
	}

	@Override
	public String[] cascadeDeleteMenu (NewAppMenuTop newAppMenuTop) {
		String[] rt = new String[2];
		rt[0] = "0";
		rt[1] = "删除失败";

		// 删除顶部菜单
		boolean pdel = delete(newAppMenuTop.getId());
		if (pdel) {
			// 获取该菜单的所有子菜单
			List<Long> topIds = new ArrayList<>();
			List<Long> menuIds = new ArrayList<>();
			List<Long> roleMenuIds = new ArrayList<>();
			getTopMenuIds(topIds, newAppMenuTop);

			// 获取顶部分类对应的菜单
			if (topIds != null && topIds.size() > 0) {
				topIds.stream().forEach(topId -> {
					NewAppMenuTop top = super.get(topId);
					QueryFilter menu_qf = new QueryFilter(NewAppMenu.class);
					menu_qf.addFilter("appname=", top.getMkey());
					List<NewAppMenu> appMenus = newAppMenuService.find(menu_qf);
					if (appMenus != null && appMenus.size() > 0) {
						appMenus.stream().forEach(appMenu -> menuIds.add(appMenu.getId()));
					}
				});
			}

			// 获取菜单与角色关系
			if (menuIds != null && menuIds.size() > 0) {
				menuIds.stream().forEach(menuId -> {
					NewAppMenu menu = newAppMenuService.get(menuId);
					QueryFilter role_qf = new QueryFilter(NewAppRoleMenu.class);
					role_qf.addFilter("menukey=", menu.getMkey());
					List<NewAppRoleMenu> roleMenus = newAppRoleMenuService.find(role_qf);
					if (roleMenus != null && roleMenus.size() > 0) {
						roleMenus.stream().forEach(roleMenu -> roleMenuIds.add(roleMenu.getId()));
					}
				});
			}

			// 删除操作
			boolean roleDel = newAppRoleMenuService.deleteBatch(roleMenuIds);
			if (roleDel) {
				boolean menuDel = newAppMenuService.deleteBatch(menuIds);
				if (menuDel) {
					boolean childDel = super.deleteBatch(topIds);
					if (childDel) {
						rt[0] = "1";
						rt[1] = "删除成功";
					} else {
						rt[0] = "0";
						rt[1] = "顶部菜单删除失败";
					}
				} else {
					rt[0] = "0";
					rt[1] = "菜单删除失败";
				}
			} else {
				rt[0] = "0";
				rt[1] = "角色和菜单关系删除失败";
			}
		}
		return rt;
	}

	/**
	 *  @author: liuchenghui
	 *  @Date: 2020/4/9 11:50
	 *  @Description: 根据删除菜单，递归找到相关联的菜单
	 */
	private void getTopMenuIds(List<Long> topIds, NewAppMenuTop newAppMenuTop) {
		QueryFilter one = new QueryFilter(NewAppMenuTop.class);
		one.addFilter("pkey=", newAppMenuTop.getMkey());
		List<NewAppMenuTop> topList = find(one);
		if (topList != null && topList.size() > 0) {
			topList.stream().forEach(top -> {
				topIds.add(top.getId());
				getChild(topIds, top);
			});
		}
	}

	/**
	 *  @author: liuchenghui
	 *  @Date: 2020/4/9 11:53
	 *  @Description: 获取子菜单
	 */
	private void getChild(List<Long> topIds, NewAppMenuTop newAppMenuTop) {
		QueryFilter two = new QueryFilter(NewAppMenuTop.class);
		two.addFilter("pkey=", newAppMenuTop.getMkey());
		List<NewAppMenuTop> topList = find(two);
		if (topList != null && topList.size() > 0) {
			topList.stream().forEach(top -> {
				topIds.add(top.getId());
				getChild(topIds, top);
			});
		}
	}

}
