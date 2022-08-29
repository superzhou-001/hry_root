/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-13 14:57:20 
 */
package hry.platform.website.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppUserStation;

/**
 * <p> AppUserStationService </p>
 *
 * @author: zhouming
 * @Date: 2020-08-13 14:57:20 
 */
public interface AppUserStationService extends BaseService<AppUserStation, Long> {
    /**
     * @param stationId 信件id
     * @param receivers 收件人Id集合
     * */
    JsonResult saveUserStation(Long stationId, String receivers, Integer userType);

    PageResult findUserStationPage(QueryFilter filter);
}
