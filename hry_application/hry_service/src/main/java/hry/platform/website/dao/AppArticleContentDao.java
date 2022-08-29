/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:12:35 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AppArticleContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> AppArticleContentDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:12:35 
 */
@Mapper
public interface AppArticleContentDao extends BaseDao<AppArticleContent, Long> {
    /**
     * 分页查询文章列表
     * @param map
     * @return
     */
    List<AppArticleContent> findPageBySql(Map<String,Object> map);

}
