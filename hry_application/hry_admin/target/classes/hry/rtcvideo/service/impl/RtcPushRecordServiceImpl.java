/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:21:18 
 */
package hry.rtcvideo.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.rtcvideo.dao.RtcPushRecordDao;
import hry.rtcvideo.model.RtcPushRecord;
import hry.rtcvideo.service.RtcPushRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * <p> RtcPushRecordService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:21:18 
 */
@Service("rtcPushRecordService")
public class RtcPushRecordServiceImpl extends BaseServiceImpl<RtcPushRecord, Long> implements RtcPushRecordService {

	@Resource(name = "rtcPushRecordDao")
	@Override
	public void setDao (BaseDao<RtcPushRecord, Long> dao) {
		super.dao = dao;
	}

	@Override
	public void saveRecord(Map<String, Object> map) {
		RtcPushRecord pushRecord = new RtcPushRecord();
		pushRecord.setTaskId(map.get("taskId").toString());
		pushRecord.setAppName(map.get("appName").toString());
		pushRecord.setChannelId(map.get("channelId").toString());
		pushRecord.setPushDomain(map.get("pushDomain").toString());
		pushRecord.setStreamName(map.get("streamName").toString());
		pushRecord.setStreamURL(map.get("streamUrl").toString());
		pushRecord.setCreated(new Date());
		((RtcPushRecordDao)dao).insert(pushRecord);
	}
}
