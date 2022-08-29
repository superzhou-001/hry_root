/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-24 10:27:25
 */
package hry.platform.config.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.config.model.AppConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p> AppConfigDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:27:25
 */
@Mapper
public interface AppConfigDao extends BaseDao<AppConfig, Long> {

    List<AppConfig> findType ();

    void batchUpdate (List<AppConfig> list);
}
