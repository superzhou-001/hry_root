package hry.rtcvideo.controller;

import com.alibaba.fastjson.JSONObject;
import com.alivc.base.ConfigMapUtil;
import com.alivc.base.ResponseResult;
import com.alivc.redis.PushStatusKey;
import com.alivc.rtcVideo.api.RtcVideoAPI;
import com.alivc.rtcVideo.pojo.RtcConfig;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import hry.core.util.QueryFilter;
import hry.redis.RedisService;
import hry.rtcvideo.model.PushInfo;
import hry.rtcvideo.model.RtcPushRecord;
import hry.rtcvideo.service.RtcFileRecordService;
import hry.rtcvideo.service.RtcPushRecordService;
import hry.rtcvideo.util.Create_Live_Url;
import hry.security.jwt.annotation.UnAuthentication;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.*;


@RestController
@RequestMapping("/rtc")
public class VideoController {

    private static final Logger LOG = LoggerFactory.getLogger(VideoController.class);

    private static String MPUTASKSTATUSRUN = "1"; //旁路运行中


    @Autowired
    private RedisService redisService;

    @Autowired
    private RtcFileRecordService rtcFileRecordService;

    @Autowired
    private RtcPushRecordService rtcPushRecordService;

    private static RtcConfig rtcConfigRemote;


   @Autowired
   public void setService(hry.rtcvideo.model.RtcConfig rtcConfig) {
       rtcConfigRemote = new RtcConfig();
       BeanUtils.copyProperties(rtcConfig,rtcConfigRemote);
   }


    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public void test() throws Exception {
        String streamURL = Create_Live_Url.generate_push_url("rtmp://talk.hurongsoft.com","huronglive9876","liveapp","livestream",3600L);
        LOG.info("streamURL="+streamURL);
    }

    /**
     * 录制回调
     * @param callbackMessage
     */
    @UnAuthentication
    @PostMapping(value = "/live/callback")
    public void liveCallback(@RequestBody String callbackMessage)  {
        if(callbackMessage!=null){
            JSONObject result = JSONObject.parseObject(callbackMessage);
            if (result != null) {
                LOG.info("callbackdata"+result.toJSONString());
                rtcFileRecordService.saveRecord(result);
            }
        }

    }

    @UnAuthentication
    @RequestMapping(value = "/publishCallback", method = RequestMethod.GET)
    public void publishCallback(HttpServletRequest request, String action, String app, String node, String id, String appname, String usrargs) throws Exception {
        PushStatusKey pushStatusKey = PushStatusKey.pushStatusKey;
        redisService.save(pushStatusKey.getPrefix()+id,action,1800);
        LOG.info("action="+action+"    app="+app+"   node="+node+"  id="+id+"   appname="+appname+"   usrargs="+usrargs);
    }

    @UnAuthentication
    @RequestMapping(value = "/getPublishStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getPublishStatus(String streamName) throws Exception {
        ResponseResult result = new ResponseResult();
        PushStatusKey pushStatusKey = PushStatusKey.pushStatusKey;
        String resultStatus = redisService.get(pushStatusKey.getPrefix()+streamName);
        result.setData(resultStatus);
        return result;
    }

    @UnAuthentication
    @RequestMapping(value = "/startMPUTask", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult startMPUTask(String channelId,String taskId) throws Exception {
        ResponseResult result = new ResponseResult();
        String requestId = "";
        String streamURL = "";
        String streamName = "";
        try {
            //推流地址
            streamName = rtcConfigRemote.getStreamName()+channelId+"_"+taskId;
            List<Object> pushInfoList = rtcConfigRemote.getPushInfos();
            PushInfo pushInfo = (PushInfo)pushInfoList.get(0);
            String pushDomain = pushInfo.getPushDomainName();
            String appName = rtcConfigRemote.getAppName();
            streamURL = Create_Live_Url.generate_push_url(pushDomain,pushInfo.getPushKey(),appName,streamName,3600L);
            //String streamURL = "rtmp://talk.hurongsoft.com/liveapp/livestream?auth_key=1591762122-0-0-e7fac50de253999a1eabdc28ab04db01";
            requestId = RtcVideoAPI.startMPUTask(channelId,taskId,streamURL,rtcConfigRemote);
            String status = RtcVideoAPI.getMPUTaskStatus(taskId,rtcConfigRemote).toString();
            if(status.equals(MPUTASKSTATUSRUN)){
                //保存旁路转推记录
                Map<String, Object> pushmap = new HashMap<>(6);
                pushmap.put("taskId",taskId);
                pushmap.put("channelId",channelId);
                pushmap.put("streamUrl",streamURL);
                pushmap.put("streamName",streamName);
                pushmap.put("pushDomain",pushDomain);
                pushmap.put("appName",appName);
                rtcPushRecordService.saveRecord(pushmap);
            }
            result.setCode(status);
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("streamURL", streamURL);
            map.put("streamName", streamName);
            result.setData(map);
            LOG.info("直播状态="+status);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            result.setResult("false");
            result.setMessage(e.getMessage());
            result.setCode(e.getErrCode());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 录制接口
     * @param channelId 频道Id
     * @param pushUrl  推流地址
     * @param command  start-开始录制,stop-结束录制
     * @return
     * @throws Exception
     */
    @UnAuthentication
    @RequestMapping(value = "/recordMPUTask", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult recordMPUTask(String channelId,String pushUrl,String taskId,String command) {
        ResponseResult result = new ResponseResult();
        LOG.info("channelId="+channelId+"   command="+command);
        rtcConfigRemote.setPlayDomainName(rtcConfigRemote.getPlayDomainNames().get(0));
        RtcVideoAPI.recordMPUTask(channelId,taskId,command,rtcConfigRemote);
        return result;
    }

    @UnAuthentication
    @RequestMapping(value = "/stopMPUTask", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult stopMPUTask(String channelId,String appId,String taskId,String streamName) throws Exception {
        ResponseResult result = new ResponseResult();
        String requestId = "";
        //结束任务
        requestId = RtcVideoAPI.stopMPUTask(channelId,taskId,streamName,rtcConfigRemote);
        result.setResult(requestId);
        QueryFilter filter = new QueryFilter(RtcPushRecord.class);
        filter.addFilter("taskId=",taskId);
        //保存结束时间
        RtcPushRecord rtcPushRecord = rtcPushRecordService.get(filter);
        rtcPushRecord.setModified(new Date());
        rtcPushRecordService.updateNull(rtcPushRecord);
        //删除推流状态
        PushStatusKey pushStatusKey = PushStatusKey.pushStatusKey;
        redisService.delete(pushStatusKey.getPrefix()+streamName);
        return result;
    }

    /**
     * 获取rtc auth
     *
     * @param
     * @return result
     * @throws Exception
     */
    @UnAuthentication
    @RequestMapping(value = "/alirtc/auth", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getAlirtcAuth(String user, String channelId) {
        ResponseResult result = new ResponseResult();
        try {
            String nonce = String.format("AK-%s", UUID.randomUUID().toString());
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.HOUR_OF_DAY, 48);
            Long timestamp = nowTime.getTimeInMillis() / 1000;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(channelId.getBytes());
            digest.update("/".getBytes());
            digest.update(user.getBytes());
            String userId = DatatypeConverter.printHexBinary(digest.digest()).toLowerCase().substring(0, 16);

            digest = MessageDigest.getInstance("SHA-256");
            digest.update(rtcConfigRemote.getAppId().getBytes());
            digest.update(rtcConfigRemote.getAppKey().getBytes());
            digest.update(channelId.getBytes());
            digest.update(userId.getBytes());
            digest.update(nonce.getBytes());
            digest.update(Long.toString(timestamp).getBytes());
            String token = DatatypeConverter.printHexBinary(digest.digest()).toLowerCase();
            Map<String, Object> map = new HashMap<String, Object>(6);
            map.put("appid", rtcConfigRemote.getAppId());
            map.put("userid", userId);
            map.put("gslb", new ArrayList<String>(){{add("https://rgslb.rtc.aliyuncs.com");}});
            map.put("token", token);
            map.put("nonce", nonce);
            map.put("timestamp", timestamp);
            map.put("channel", channelId);

            result.setData(map);
        } catch (Exception e) {
            result.setCode("400");
            result.setResult("false");
            LOG.error("--- 获取凭证异常", e);
        }
        return result;

    }
}
