/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午7:19:24
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月9日 下午7:19:24
 */
@Mapper
public interface NewAppUserRoleDao extends  BaseDao<NewAppUserRole, Long>{

    List<NewAppUserRole> findUserRoleList(String userid);

}
