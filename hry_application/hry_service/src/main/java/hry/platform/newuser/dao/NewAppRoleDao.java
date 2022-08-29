/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年9月21日 上午11:25:10
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年9月21日 上午11:25:10
 */
@Mapper
public interface NewAppRoleDao extends BaseDao<NewAppRole, Long> {

}
