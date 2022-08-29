/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:10:31 
 */
package hry.platform.website.service;

import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.platform.website.model.AdContentManage;

/**
 * <p> AdContentManageService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:10:31 
 */
public interface AdContentManageService extends BaseService<AdContentManage, Long> {

    /**
     * 根据条件查询企业信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(hry.core.util.QueryFilter filter);

}
