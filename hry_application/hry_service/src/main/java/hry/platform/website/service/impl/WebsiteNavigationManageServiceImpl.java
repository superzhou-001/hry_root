/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:08:12 
 */
package hry.platform.website.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.website.model.WebsiteNavigationManage;
import hry.platform.website.service.WebsiteNavigationManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> WebsiteNavigationManageService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:08:12 
 */
@Service("websiteNavigationManageService")
public class WebsiteNavigationManageServiceImpl extends BaseServiceImpl<WebsiteNavigationManage, Long> implements WebsiteNavigationManageService {

	@Resource(name = "websiteNavigationManageDao")
	@Override
	public void setDao (BaseDao<WebsiteNavigationManage, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<WebsiteNavigationManage> findWebSiteTree() {
		// 查询根节点
		QueryFilter filter = new QueryFilter(WebsiteNavigationManage.class);
		filter.addFilter("pkey=", "root");
		filter.setOrderby("sort asc");
		List<WebsiteNavigationManage> rootList = find(filter);
		if (rootList != null && rootList.size() > 0) {
			for (WebsiteNavigationManage site : rootList) {
				createTreeData(site);
			}
		}
		return rootList;
	}

	/**
	 * 递归生成树结构
	 * */
	private void createTreeData(WebsiteNavigationManage site) {
		QueryFilter filter = new QueryFilter(WebsiteNavigationManage.class);
		filter.addFilter("pkey=", site.getNkey());
		filter.setOrderby("sort asc");
		List<WebsiteNavigationManage> childrenList = find(filter);
		if (childrenList != null && childrenList.size() > 0) {
			site.setChildren(childrenList);
			for (WebsiteNavigationManage webSite : childrenList) {
				createTreeData(webSite);
			}
		} else {
			site.setChildren(new ArrayList<>());
		}
	}
}
