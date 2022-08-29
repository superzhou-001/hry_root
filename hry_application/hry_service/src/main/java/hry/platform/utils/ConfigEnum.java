package hry.platform.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *  @author: liuchenghui
 *  @Date: 2020/4/17 16:34
 *  @Description: 配置枚举类
 */
public enum ConfigEnum {

    LOG_MQ("requestLogger", "reqLogRecieved"),
    TEST_MQ("test", "testMethod");

    /**
     * @Description: 队列名
     */
    private String queueName;
    /**
     * @Description: 方法名
     */
    private String methodName;

    ConfigEnum(String queueName, String methodName) {
        this.queueName = queueName;
        this.methodName = methodName;
    }

    public String getQueueName () {
        return queueName;
    }

    public void setQueueName (String queueName) {
        this.queueName = queueName;
    }

    public String getMethodName () {
        return methodName;
    }

    public void setMethodName (String methodName) {
        this.methodName = methodName;
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/17 17:00
     *  @Description: 获取队列与方法的映射
     */
    public static Map<String, String> getQueueAndMethod() {
        Map<String, String> map = new HashMap<>();
        ConfigEnum[] configs = values();
        if (configs != null && configs.length > 0) {
            for (ConfigEnum cfg : configs) {
                map.put(cfg.getQueueName(), cfg.getMethodName());
            }
        }
        return map;
    }
}
