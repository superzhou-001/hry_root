/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:37:13 
 */
package hry.platform.website.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AppProduct;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppProductDao </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:37:13 
 */
@Mapper
public interface AppProductDao extends BaseDao<AppProduct, Long> {

}
