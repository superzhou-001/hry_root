/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-30 11:37:30 
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.NewAppOrganizationCharge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> NewAppOrganizationChargeDao </p>
 *
 * @author: zhouming
 * @Date: 2020-06-30 11:37:30 
 */
@Mapper
public interface NewAppOrganizationChargeDao extends BaseDao<NewAppOrganizationCharge, Long> {
    List<NewAppOrganizationCharge> findPageBySql(Map<String, Object> map);
}
