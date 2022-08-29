/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-30 11:37:30 
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppOrganizationCharge;

/**
 * <p> NewAppOrganizationChargeService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-30 11:37:30 
 */
public interface NewAppOrganizationChargeService extends BaseService<NewAppOrganizationCharge, Long> {

    JsonResult addOrganizationCharge(Long organizationId, String userIds);

    JsonResult removeOrganizationCharge(Long organizationId, String userIds);

    PageResult findPageOrganizationCharge(QueryFilter filter);
}
