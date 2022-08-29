/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:16:11 
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppUserUpordown;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> NewAppUserUpordownDao </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:16:11 
 */
@Mapper
public interface NewAppUserUpordownDao extends BaseDao<NewAppUserUpordown, Long> {

    public List<NewAppUserUpordown> findUserUpOrDownList(Map<String, Object> map);
}
