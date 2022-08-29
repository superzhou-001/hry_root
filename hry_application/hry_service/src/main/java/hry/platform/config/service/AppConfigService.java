/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-24 10:27:25
 */
package hry.platform.config.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.platform.config.model.AppConfig;

import java.util.List;

/**
 * <p> AppConfigService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:27:25
 */
public interface AppConfigService extends BaseService<AppConfig, Long> {

    String getValueByKey (String key);

    void initCache ();

    void batchUpdate (List<AppConfig> list);

    /**
     * 根据typeKey查询网站参数
     * @param typeKey
     * @return
     */
    public JsonResult dataByTypeKey(String typeKey);
}
