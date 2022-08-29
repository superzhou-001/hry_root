/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:09:41 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AdPositionManage;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AdPositionManageDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:09:41 
 */
@Mapper
public interface AdPositionManageDao extends BaseDao<AdPositionManage, Long> {

}
