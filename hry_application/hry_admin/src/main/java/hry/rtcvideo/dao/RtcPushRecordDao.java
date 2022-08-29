/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:21:18 
 */
package hry.rtcvideo.dao;

import hry.core.mvc.dao.BaseDao;
import hry.rtcvideo.model.RtcPushRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> RtcPushRecordDao </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:21:18 
 */
@Mapper
public interface RtcPushRecordDao extends BaseDao<RtcPushRecord, Long> {

}
