/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:13:47 
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUserPost;

import java.util.Map;

/**
 * <p> NewAppUserPostService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:13:47 
 */
public interface NewAppUserPostService extends BaseService<NewAppUserPost, Long> {
    /*
     * 批量添加岗位人员
     * */
    JsonResult addPostUser(long postId, String userIds);

    /*
     * 批量删除岗位人员
     * */
    JsonResult removePostUser(long postId, String userIds);

    /**
     * 查询岗位下人员集合
     * */
    PageResult findPostUserPageList(QueryFilter filter);

    JsonResult findUserPost(String userid);
}
