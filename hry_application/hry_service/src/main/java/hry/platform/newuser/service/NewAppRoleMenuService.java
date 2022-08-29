/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午7:25:37
 */
package hry.platform.newuser.service;


import hry.core.mvc.service.BaseService;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppRoleMenu;

import java.util.List;

/**
 *
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2016年6月16日 上午10:35:55
 */
public interface NewAppRoleMenuService extends BaseService<NewAppRoleMenu, Long> {

    List<String> findUserMenuRoleByuserId(String s);

    List<NewAppMenu> findMenuRoleByuserId(Object id);

    List<NewAppMenu> loadmenubyroleid(Long roleid);
}
