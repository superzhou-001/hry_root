/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:08:12 
 */
package hry.platform.website.service;

import hry.core.mvc.service.BaseService;
import hry.platform.website.model.WebsiteNavigationManage;

import java.util.List;

/**
 * <p> WebsiteNavigationManageService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:08:12 
 */
public interface WebsiteNavigationManageService extends BaseService<WebsiteNavigationManage, Long> {

    public List<WebsiteNavigationManage> findWebSiteTree();
}
