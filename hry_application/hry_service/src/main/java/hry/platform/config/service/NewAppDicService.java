/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-24 10:28:18
 */
package hry.platform.config.service;

import hry.core.mvc.service.BaseService;
import hry.platform.config.model.NewAppDic;

import java.util.List;

/**
 * <p> NewAppDicService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:28:18
 */
public interface NewAppDicService extends BaseService<NewAppDic, Long> {

    void flushRedis ();

    void initAreaCache ();

    void initDicTree ();

    List<NewAppDic> findDicTree ();

    void flushTreeRedis(String pkey);

    List<NewAppDic> findDicList(String pkey);

}
