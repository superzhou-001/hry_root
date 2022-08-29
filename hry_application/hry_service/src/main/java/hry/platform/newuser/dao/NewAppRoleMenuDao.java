/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午7:19:24
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色  应用菜单关联表
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2016年6月16日 上午10:35:03
 */
@Mapper
public interface NewAppRoleMenuDao extends  BaseDao<NewAppRoleMenu, Long>{

    List<String> findUserMenuRoleByuserId(Map<String, Object> param);

    List<NewAppMenu> findMenuRoleByuserId(Map<String, Object> param);

    List<NewAppMenu> loadmenubyroleid(Map<String, Object> param);
}
