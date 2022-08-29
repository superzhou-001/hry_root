package hry.rtcvideo.model;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "rtc")
public class RtcConfig {

    private String regionId;
    private String appId;
    private String appKey;
    private String accessKeyId;
    private String accessKeySecret;
    private List<String> playDomainNames;//播流域名集合
    private String appName;
    private String streamName;
    private List<PushInfo> pushInfos;//推流信息
    private String playDomainName;//当前传值播流域名
    private OssConfig oss;


    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public List<String> getPlayDomainNames() {
        return playDomainNames;
    }

    public void setPlayDomainNames(List<String> playDomainNames) {
        this.playDomainNames = playDomainNames;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getPlayDomainName() {
        return playDomainName;
    }

    public void setPlayDomainName(String playDomainName) {
        this.playDomainName = playDomainName;
    }

    public List<PushInfo> getPushInfos() {
        return pushInfos;
    }

    public void setPushInfos(List<PushInfo> pushInfos) {
        this.pushInfos = pushInfos;
    }

    public OssConfig getOss() {
        return oss;
    }

    public void setOss(OssConfig oss) {
        this.oss = oss;
    }
}
