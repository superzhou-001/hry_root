/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:12:35 
 */
package hry.platform.website.service.impl;

import com.github.pagehelper.Page;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.website.dao.AppArticleContentDao;
import hry.platform.website.model.AppArticleCategory;
import hry.platform.website.model.AppArticleContent;
import hry.platform.website.service.AppArticleCategoryService;
import hry.platform.website.service.AppArticleContentService;
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
 * <p> AppArticleContentService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:12:35 
 */
@Service("appArticleContentService")
public class AppArticleContentServiceImpl extends BaseServiceImpl<AppArticleContent, Long> implements AppArticleContentService {

	@Autowired
	private AppArticleCategoryService appArticleCategoryService;

	@Resource(name = "appArticleContentDao")
	@Override
	public void setDao (BaseDao<AppArticleContent, Long> dao) {
		super.dao = dao;
	}

	@Override
	public PageResult findPageByResult(HttpServletRequest request) {
		QueryFilter filter = new QueryFilter(AppArticleContent.class, request);
		String categoryKey = request.getParameter("categoryKey");
		String categoryIds = "";
		List<Long> ids = new ArrayList<>();
		getChildrenIds(categoryKey, ids);
		for (Long id : ids) {
			if ("".equals(categoryIds)) {
				categoryIds += id.toString();
			} else {
				categoryIds = categoryIds + "," + id;
			}
		}
		filter.addFilter("categoryId_in", categoryIds);
		PageResult result = super.findPageResult(filter);
		List<AppArticleContent> contents = result.getRows();
		contents.forEach((con) -> {
			AppArticleCategory category = appArticleCategoryService.get(con.getCategoryId());
			con.setCategoryName(category.getCategoryName());
		});
		return result;
	}

	private void getChildrenIds(String categoryKey, List<Long> ids) {
		QueryFilter filter = new QueryFilter(AppArticleCategory.class);
		filter.addFilter("categoryKey=", categoryKey);
		filter.addFilter("isShow=", 1);
		filter.addFilter("isPage=", 0);
		AppArticleCategory category = appArticleCategoryService.get(filter);
		QueryFilter queryFilter = new QueryFilter(AppArticleCategory.class);
		queryFilter.addFilter("pCategoryKey=", categoryKey);
		queryFilter.addFilter("isShow=", 1);
		List<AppArticleCategory> categoryList = appArticleCategoryService.find(queryFilter);
		if (categoryList != null && categoryList.size() > 0) {
			categoryList.forEach((category1 -> {
				getChildrenIds(category1.getCategoryKey(), ids);
			}));
		} else {
			if (category != null) {
				ids.add(category.getId());
			}
		}
	}


	@Override
	public List<Map<String, Object>> findByCategoryList(String categoryKey) {
		List<Map<String, Object>> result = new ArrayList<>();
		String pname = ""; // 父级分类名称
		if ("root".equals(categoryKey)) {
			pname = "文章分类管理";
		} else {
			QueryFilter filter = new QueryFilter(AppArticleCategory.class);
			filter.addFilter("categoryKey=", categoryKey);
			filter.addFilter("isShow=", 1);
			AppArticleCategory category = appArticleCategoryService.get(filter);
			pname = category != null ? category.getCategoryName() : "";
		}
		findCategoryClass(categoryKey, pname, result);
		return result;
	}



	/**
	 * 查询文章类别层级
	 * @param categoryKey 当前节点分类key
	 * @param pname 当前节点名称
	 * @param result 总数据集合
	 */
	private void findCategoryClass(String categoryKey, String pname, List<Map<String, Object>> result) {
		// 例：文章分类管理-->公告动态-->平台公告
		// 先查询该分类下的子级分类
		QueryFilter filter = new QueryFilter(AppArticleCategory.class);
		filter.addFilter("pCategoryKey=",categoryKey);
		filter.addFilter("isShow=", 1);
		List<AppArticleCategory> categoryList = appArticleCategoryService.find(filter);
		Map<String, Object> map = new HashMap<>();
		if (categoryList != null && categoryList.size() > 0) {
			categoryList.stream().forEach((category) -> {
				map.put("langName", pname + "-->" + category.getCategoryName());
				findCategoryClass(category.getCategoryKey(), map.get("langName").toString(), result);
			});
		} else {
			// 只查出 isPage 是列表属性
			QueryFilter queryFilter = new QueryFilter(AppArticleCategory.class);
			queryFilter.addFilter("categoryKey=",categoryKey);
			queryFilter.addFilter("isShow=", 1);
			queryFilter.addFilter("isPage=", 0);
			queryFilter.setOrderby("sort ASC");
			AppArticleCategory category = appArticleCategoryService.get(queryFilter);
			if (category != null) {
				map.put("langName", pname);
				map.put("categoryName", category.getCategoryName());
				map.put("categoryId", category.getId());
				map.put("isPage", category.getIsPage());
				map.put("pCategoryKey", categoryKey);
				result.add(map);
			}
		}


	}

	@Override
	public PageResult findPageBySql(QueryFilter filter) {
		// ----------------------分页查询头部外壳------------------------------
		String categoryKey = filter.getRequest().getParameter("categoryKey");
		String labelName = filter.getRequest().getParameter("labelName");//文章标签
		String keyword = filter.getRequest().getParameter("keyword");//搜索关键字
		String categoryIds = "";
		List<Long> ids = new ArrayList<>();
		getChildrenIds(categoryKey, ids);
		for (Long id : ids) {
			if ("".equals(categoryIds)) {
				categoryIds += id.toString();
			} else {
				categoryIds = categoryIds + "," + id;
			}
		}
		Page<AppArticleContent> page = PageFactory.getPage(filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryIds",categoryIds.split(","));
		if(!StringUtils.isEmpty(labelName)){
			map.put("labelName",labelName);
		}
		if(!StringUtils.isEmpty(keyword)){
			map.put("keyword","%"+keyword+"%");
		}
		((AppArticleContentDao) dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}
}
