/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:26:51 
 */
package hry.platform.config.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.platform.config.model.AppHolidaysInfo;

import java.util.Map;

/**
 * <p> AppHolidaysInfoService </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:26:51 
 */
public interface AppHolidaysInfoService extends BaseService<AppHolidaysInfo, Long> {
        /**
         * 节假日明细网络同步
         * */
        JsonResult networkSyncDate(Long yearId);

        /**
         * 获取节假日区间
         * */
        JsonResult findHolidayInterval(Long yearId);

        /**
         * 校验区间是否合法
         * */
        JsonResult addHoliday(Map<String, String> map);

        /**
         * 删除节假日
         * */
        JsonResult delHoliday(Map<String, String> map);

}
