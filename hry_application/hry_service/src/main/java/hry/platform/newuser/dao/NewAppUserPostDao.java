/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:13:47 
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppUserPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> NewAppUserPostDao </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:13:47 
 */
@Mapper
public interface NewAppUserPostDao extends BaseDao<NewAppUserPost, Long> {
    public List<NewAppUserPost> findPageBySql(Map<String, Object> map);

    public List<NewAppUserPost> findUserPostList(String userid);
}
