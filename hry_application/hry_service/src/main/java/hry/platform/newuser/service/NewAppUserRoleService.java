/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午7:25:37
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.platform.newuser.model.NewAppUserRole;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月9日 下午7:25:37
 */
public interface NewAppUserRoleService extends BaseService<NewAppUserRole, Long>{

    JsonResult findUserRole(String userid);
}
