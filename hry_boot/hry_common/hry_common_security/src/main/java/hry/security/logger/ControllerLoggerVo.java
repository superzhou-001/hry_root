package hry.security.logger;

import lombok.Data;

import java.io.Serializable;

/**
 *  @author: liuchenghui
 *  @Date: 2020/4/10 19:15
 *  @Description: 日志消息类
 */
@Data
public class ControllerLoggerVo implements Serializable {

    /**
     * @Description: 用户名
     */
    private String userName;
    /**
     * @Description: 请求路径
     */
    private String url;
    /**
     * @Description: 访问ip
     */
    private String ip;
    /**
     * @Description: 访问端口
     */
    private int port;
    /**
     * @Description: 方法说明
     */
    private String methodDesc;
    /**
     * @Description: 方法名
     */
    private String methodName;
    /**
     * @Description: 请求参数
     */
    private String args;
    /**
     * @Description: 类地址
     */
    private String target;
    /**
     * @Description: 来源
     */
    private String source;
    /**
     * @Description: 备注
     */
    private String remark;

}
