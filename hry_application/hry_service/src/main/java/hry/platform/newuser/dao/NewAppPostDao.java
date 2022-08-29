/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:11:00 
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppPost;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> NewAppPostDao </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:11:00 
 */
@Mapper
public interface NewAppPostDao extends BaseDao<NewAppPost, Long> {

}
