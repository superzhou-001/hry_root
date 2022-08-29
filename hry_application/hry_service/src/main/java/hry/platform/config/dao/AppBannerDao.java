/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-05-09 14:12:32 
 */
package hry.platform.config.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.config.model.AppBanner;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppBannerDao </p>
 *
 * @author: zhouming
 * @Date: 2020-05-09 14:12:32 
 */
@Mapper
public interface AppBannerDao extends BaseDao<AppBanner, Long> {

}
