/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-24 10:28:18 
 */
package hry.platform.config.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.config.model.NewAppDic;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> NewAppDicDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:28:18 
 */
@Mapper
public interface NewAppDicDao extends BaseDao<NewAppDic, Long> {

}
