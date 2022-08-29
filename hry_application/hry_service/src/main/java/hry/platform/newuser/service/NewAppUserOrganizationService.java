/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午7:25:37
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUserOrganization;

import java.util.List;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月9日 下午7:25:37
 */
public interface NewAppUserOrganizationService extends BaseService<NewAppUserOrganization, Long>{
    List<NewAppUserOrganization> findUserOrganization(String userid);

    JsonResult addOrganizationUser(Long organizationId, String userIds);

    JsonResult removeOrganizationUser(Long organizationId, String userIds);

    PageResult findPageUserOrganization(QueryFilter filter);
}
