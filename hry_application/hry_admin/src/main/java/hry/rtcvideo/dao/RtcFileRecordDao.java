/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:24:59 
 */
package hry.rtcvideo.dao;

import hry.core.mvc.dao.BaseDao;
import hry.rtcvideo.model.RtcFileRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> RtcFileRecordDao </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:24:59 
 */
@Mapper
public interface RtcFileRecordDao extends BaseDao<RtcFileRecord, Long> {

}
