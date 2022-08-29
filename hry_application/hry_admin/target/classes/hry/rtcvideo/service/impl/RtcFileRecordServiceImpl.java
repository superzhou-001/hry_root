/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:24:59 
 */
package hry.rtcvideo.service.impl;

import com.alibaba.fastjson.JSONObject;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.rtcvideo.dao.RtcFileRecordDao;
import hry.rtcvideo.model.RtcFileRecord;
import hry.rtcvideo.service.RtcFileRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p> RtcFileRecordService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:24:59 
 */
@Service("rtcFileRecordService")
public class RtcFileRecordServiceImpl extends BaseServiceImpl<RtcFileRecord, Long> implements RtcFileRecordService {

	@Resource(name = "rtcFileRecordDao")
	@Override
	public void setDao (BaseDao<RtcFileRecord, Long> dao) {
		super.dao = dao;
	}

	@Override
	public void saveRecord(JSONObject result) {
		//{"app":"liveapp","duration":47.533,"start_time":1593586367,"stop_time":1593586414,"stream":"livestream0701_858ba24f-519a-4095-82b6-3855ea9c204f","domain":"play.hurongsoft.com","uri":"record/liveapp/2020-07-01/livestream0701_858ba24f-519a-4095-82b6-3855ea9c204f/02020-07-01-14-52-522020-07-01-14-53-33.m3u8"}
		RtcFileRecord fileRecord = new RtcFileRecord();
		fileRecord.setAppName(result.getString("app"));
		fileRecord.setDuration(result.getBigDecimal("duration"));
		fileRecord.setStartTime(new Date(result.getLongValue("start_time")));
		fileRecord.setStopTime(new Date(result.getLongValue("stop_time")));
		fileRecord.setStreamName(result.getString("stream"));
		fileRecord.setPlayDomain(result.getString("domain"));
		String fileUrl = result.getString("uri");
		fileRecord.setUri(fileUrl);
		fileRecord.setTaskId(fileUrl.split("_")[1].split("/")[0]);
		fileRecord.setFileType(fileUrl.split("\\.")[1]);
		fileRecord.setCreated(new Date());
		fileRecord.setModified(new Date());
		((RtcFileRecordDao)dao).insertSelective(fileRecord);

	}
}
