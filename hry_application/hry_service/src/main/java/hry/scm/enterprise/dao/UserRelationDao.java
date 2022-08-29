/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:41:09 
 */
package hry.scm.enterprise.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.enterprise.model.UserRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> UserRelationDao </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:41:09 
 */
@Mapper
public interface UserRelationDao extends BaseDao<UserRelation, Long> {

}
