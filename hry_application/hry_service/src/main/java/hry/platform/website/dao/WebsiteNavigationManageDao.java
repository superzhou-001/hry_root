/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:08:12 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.WebsiteNavigationManage;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> WebsiteNavigationManageDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:08:12 
 */
@Mapper
public interface WebsiteNavigationManageDao extends BaseDao<WebsiteNavigationManage, Long> {

}
