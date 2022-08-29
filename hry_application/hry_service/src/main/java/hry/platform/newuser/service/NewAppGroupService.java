/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:18:03 
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.platform.newuser.model.NewAppGroup;

/**
 * <p> NewAppGroupService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:18:03 
 */
public interface NewAppGroupService extends BaseService<NewAppGroup, Long> {
    public JsonResult addGroup(NewAppGroup newAppGroup);
}
