/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:16:11 
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUserUpordown;

import java.util.Map;

/**
 * <p> NewAppUserUpordownService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:16:11 
 */
public interface NewAppUserUpordownService extends BaseService<NewAppUserUpordown, Long> {

    public JsonResult addUserUpOrDown(String userid, String upOtheruserids, String downOtheruserids);

    public JsonResult findUserUpOrDownList(Map<String, Object> paramMap);

    public PageResult findPageUserUpOrDownList(QueryFilter filter);
}
