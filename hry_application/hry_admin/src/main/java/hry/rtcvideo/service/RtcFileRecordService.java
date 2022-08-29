/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:24:59 
 */
package hry.rtcvideo.service;

import com.alibaba.fastjson.JSONObject;
import hry.core.mvc.service.BaseService;
import hry.rtcvideo.model.RtcFileRecord;

/**
 * <p> RtcFileRecordService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:24:59 
 */
public interface RtcFileRecordService extends BaseService<RtcFileRecord, Long> {

       public void saveRecord(JSONObject result);

}
