/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:11:54 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AppArticleCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppArticleCategoryDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:11:54 
 */
@Mapper
public interface AppArticleCategoryDao extends BaseDao<AppArticleCategory, Long> {

}
