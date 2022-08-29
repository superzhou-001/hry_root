/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:21:18 
 */
package hry.rtcvideo.service;

import hry.core.mvc.service.BaseService;
import hry.rtcvideo.model.RtcPushRecord;

import java.util.Map;

/**
 * <p> RtcPushRecordService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:21:18 
 */
public interface RtcPushRecordService extends BaseService<RtcPushRecord, Long> {

    public void saveRecord(Map<String, Object> map);
}
