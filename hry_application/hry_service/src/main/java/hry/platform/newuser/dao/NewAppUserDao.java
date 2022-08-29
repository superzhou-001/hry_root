/**
 * Copyright:    互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-05-27 16:05:55
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * <p> AppUserDao </p>
 * @author:         liushilei
 * @Date :          2017-05-27 16:05:55
 */
@Mapper
public interface NewAppUserDao extends  BaseDao<NewAppUser, Long> {

	/**
	 * sql 分页
	 * @param map
	 * @return
	 */
	List<NewAppUser> findPageBySql(Map<String, Object> map);

	/***
	 * 查询未加入组织人员
	 * */
	List<NewAppUser> findNotUserOrganList(Map<String, Object> map);

}
