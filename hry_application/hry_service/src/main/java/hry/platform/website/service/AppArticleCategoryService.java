/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:11:54 
 */
package hry.platform.website.service;

import hry.core.mvc.service.BaseService;
import hry.platform.website.model.AppArticleCategory;

import java.util.List;

/**
 * <p> AppArticleCategoryService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:11:54 
 */
public interface AppArticleCategoryService extends BaseService<AppArticleCategory, Long> {
    public List<AppArticleCategory> findArticleTree();
}
