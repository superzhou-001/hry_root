/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:12:35 
 */
package hry.platform.website.service;

import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppArticleContent;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p> AppArticleContentService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:12:35 
 */
public interface AppArticleContentService extends BaseService<AppArticleContent, Long> {
    public PageResult findPageByResult(HttpServletRequest request);
    public List<Map<String, Object>> findByCategoryList(String categoryKey);
    /**
     * 网站端查询文章列表
     * @param filter
     * @return
     */
    public PageResult findPageBySql(QueryFilter filter);

}
