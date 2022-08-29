/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:18:03 
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> NewAppGroupDao </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:18:03 
 */
@Mapper
public interface NewAppGroupDao extends BaseDao<NewAppGroup, Long> {

}
