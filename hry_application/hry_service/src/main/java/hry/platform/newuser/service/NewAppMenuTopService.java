/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-31 16:04:36
 */
package hry.platform.newuser.service;

import hry.core.mvc.service.BaseService;
import hry.platform.newuser.model.NewAppMenuTop;

/**
 * <p> NewAppMenuTopService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-31 16:04:36
 */
public interface NewAppMenuTopService extends BaseService<NewAppMenuTop, Long> {

    Integer getMaxOrderNo (String pkey);

    String[] cascadeDeleteMenu (NewAppMenuTop newAppMenuTop);
}
