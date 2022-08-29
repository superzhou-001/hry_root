package hry.rtcvideo.util;

import com.alivc.redis.LiveKey;
import hry.redis.RedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Create_Live_Url {

    private static RedisService redisService;

    @Autowired
    public void setService(RedisService redisService) {

        Create_Live_Url.redisService = redisService;

    }

    /**
     * 计算md5
     * @param param
     * @return
     */
    public static String md5(String param) {
        if(param == null || param.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(param.getBytes());
            byte[] byteArray = md5.digest();

            BigInteger bigInt = new BigInteger(1, byteArray);
            // 参数16表示16进制
            String result = bigInt.toString(16);
            // 不足32位高位补零
            while(result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 生成推流地址
     * @param pushDomain 推流域名
     * @param pushKey 推流域名配置的鉴权Key
     * @param appName 推流AppName
     * @param streamName 推流StreamName
     * @param expireTime 过期时间（单位是秒）
     */
    public static String  generate_push_url(String pushDomain,String pushKey,String appName,String streamName,long expireTime) {
        LiveKey liveKey = LiveKey.liveKey;
        String url = "rtmp://"+pushDomain+"/"+appName+"/"+streamName;
        String pushUrl = "";
        pushUrl = redisService.get(liveKey.getPrefix()+url);
        if(StringUtils.isEmpty(pushUrl)){
            //推流域名未开启鉴权功能的情况下
            if(pushKey==""){
                pushUrl = url;
            }else {
                long timeStamp = System.currentTimeMillis()/1000L + expireTime;
                String stringToMd5 = "/"+appName+"/"+streamName+"-"+Long.toString(timeStamp)+"-0-0-"+pushKey;
                String authKey = md5(stringToMd5);
                pushUrl = url+"?auth_key="+Long.toString(timeStamp)+"-0-0-"+authKey;
            }
            redisService.save(liveKey.getPrefix()+url,pushUrl,liveKey.expireSeconds());
        }

        return pushUrl;
    }
    /**
     * 生成播放地址
     * @param pullDomain 播放域名
     * @param pullKey 播放鉴权Key
     * @param appName 播放appName（同推流appName)
     * @param streamName 播放streamName (同推流streamName）
     * @param expireTime 过期时间（单位是秒
     */
    public static void general_pull_url(String pullDomain,String pullKey,String appName,String streamName,long expireTime) {
        String rtmpUrl = ""; //rtmp的拉流地址
        String hlsUrl = "";  //m3u8的拉流地址
        String flvUrl = "";  //flv的拉流地址
        //播放域名未配置鉴权Key的情况下
        if(pullKey == "") {
            rtmpUrl = "rtmp://"+pullDomain+"/"+appName+"/"+streamName;
            hlsUrl = "http://"+pullDomain+"/"+appName+"/"+streamName+".m3u8";
            flvUrl = "http://"+pullDomain+"/"+appName+"/"+streamName+".flv";
        }else {
            long timeStamp = System.currentTimeMillis()/1000L + expireTime;
            String rtmpToMd5 = "/"+appName+"/"+streamName+"-"+Long.toString(timeStamp)+"-0-0-"+pullKey;
            String rtmpAuthKey = md5(rtmpToMd5);
            rtmpUrl = "rtmp://"+pullDomain+"/"+appName+"/"+streamName+"?auth_key="+Long.toString(timeStamp)+"-0-0-"+rtmpAuthKey;

            String hlsToMd5 = "/"+appName+"/"+streamName+".m3u8-"+Long.toString(timeStamp)+"-0-0-"+pullKey;
            String hlsAuthKey = md5(hlsToMd5);
            hlsUrl = "http://"+pullDomain+"/"+appName+"/"+streamName+".m3u8"+"?auth_key="+Long.toString(timeStamp)+"-0-0-"+hlsAuthKey;

            String flvToMd5 = "/"+appName+"/"+streamName+".flv-"+Long.toString(timeStamp)+"-0-0-"+pullKey;
            String flvAuthKey = md5(flvToMd5);
            flvUrl = "http://"+pullDomain+"/"+appName+"/"+streamName+".flv"+"?auth_key="+Long.toString(timeStamp)+"-0-0-"+flvAuthKey;
        }

        System.out.println("RTMP播放地址为： "+rtmpUrl);
        System.out.println("m3u8播放地址为： "+hlsUrl);
        System.out.println("flv播放地址为： "+flvUrl);
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //生成长度为5的随机字符串作为appName和streamName(字母和数字组合)
        String appName = RandomStringUtils.randomAlphanumeric(5);;
        String streamName = RandomStringUtils.randomAlphanumeric(5);;

        long expireTime = 3600L;
        String pullDomain = "mxl-pull.pier39.cn";
        String pullKey = "querty1234";

        String pushDomain = "mxl-push.pier39.cn";
        String pushKey = "querty123";
        com.alivc.rtcVideo.util.Create_Live_Url.general_pull_url(pullDomain, pullKey, appName, streamName, expireTime);
        com.alivc.rtcVideo.util.Create_Live_Url.generate_push_url(pushDomain, pushKey, appName, streamName, expireTime);
    }
}
