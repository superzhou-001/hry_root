/**
 * Copyright:   互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-06-01 19:44:41
 */
package hry.platform.newuser.service;

import hry.core.mvc.service.BaseService;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppMenuTop;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p> AppMenuService </p>
 * @author:         liushilei
 * @Date :          2017-06-01 19:44:41
 */
public interface NewAppMenuService extends BaseService<NewAppMenu, Long> {

	List<NewAppMenu> listTree(HttpServletRequest request);

    List<NewAppMenu> findTree(String appName, String shiroUrl);

    void initMenu ();

    void syncMenu (NewAppMenuTop newAppMenuTop);

    String[] cascadeDeleteMenu (NewAppMenu appMenu);
}
