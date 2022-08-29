/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月2日 下午4:28:01
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppOrganization;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月2日 下午4:28:01
 */
@Mapper
public interface NewAppOrganizationDao extends  BaseDao<NewAppOrganization, Long> {

	/**
	 * <p> TODO</p>     查询一个组织结构下的子组织结构
	 * @author:         Liu Shilei
	 * @param:    @param appOrganization
	 * @return: void
	 * @Date :          2015年12月25日 下午5:32:33
	 * @throws:
	 */
	List<NewAppOrganization> findSonOrganization(Long id);

}
