/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:11:54 
 */
package hry.platform.website.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppArticleCategory;
import hry.platform.website.service.AppArticleCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> AppArticleCategoryService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:11:54 
 */
@Service("appArticleCategoryService")
public class AppArticleCategoryServiceImpl extends BaseServiceImpl<AppArticleCategory, Long> implements AppArticleCategoryService {

	@Resource(name = "appArticleCategoryDao")
	@Override
	public void setDao (BaseDao<AppArticleCategory, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<AppArticleCategory> findArticleTree() {
		QueryFilter filter = new QueryFilter(AppArticleCategory.class);
		filter.addFilter("categoryKey=","root");
		List<AppArticleCategory> list = super.find(filter);
		list.forEach((article) ->{
			createTreeData(article);
		});
		return list;
	}

	// 递归生成树
	private void createTreeData (AppArticleCategory category) {
		QueryFilter filter = new QueryFilter(AppArticleCategory.class);
		filter.addFilter("pCategoryKey=", category.getCategoryKey());
		List<AppArticleCategory> list = super.find(filter);
		if (list != null && list.size() > 0) {
			category.setChildren(list);
			list.forEach((article) -> {
				createTreeData(article);
			});
		} else {
			category.setChildren(new ArrayList<>());
		}
	}

}
