package hry.platform.rmq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import hry.core.util.QueryFilter;
import hry.platform.config.model.RequestLogRecord;
import hry.platform.config.service.RequestLogRecordService;
import hry.platform.newuser.model.AppLoginLog;
import hry.platform.newuser.service.AppLoginLogService;
import hry.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author zhouming
 * @date 2020/6/24 16:34
 * @Version 1.0
 */
@Component
@Slf4j
public class MsgReceiverRetryListener {
    /**
     *  @author: zhouming
     *  @Date: 2020/5/22 10:30
     *  @Description: 消息消费
     *  @RabbitHandler 代表此方法为接受到消息后的处理方法，并实现了RabbitMq的重试机制
     *  queuesToDeclare = @Queue(value="requestLogger", durable="true" )
     *  自动创建对列并设置是否持久化
     * */
    @RabbitListener(queuesToDeclare = @Queue(value="requestLogger", durable="true" ))
    public void process(Message message, Channel channel) throws IOException {
        // 消息处理
        String msg = new String(message.getBody(), Charset.defaultCharset());
        RequestLogRecordService requestLogRecordService = SpringUtil.getBean("requestLogRecordService");
        log.info("接收处理队列当中的消息---请求日志：{}", msg);
        if (StringUtils.isNotEmpty(msg)) {
            RequestLogRecord logRecord = JSONObject.parseObject(msg, RequestLogRecord.class);
            // 参数截取
            if (logRecord.getArgs() != null && logRecord.getArgs().length() > 1000) {
                String ags = logRecord.getArgs().substring(0, 1000);
                logRecord.setArgs(ags);
            }
            requestLogRecordService.save(logRecord);
            // 向es添加请求日志
            try {
                logRecord.setCreatedTime(System.currentTimeMillis());
                requestLogRecordService.saveToEs(logRecord);
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
            log.info("日志保存入库成功");
        }
    }


    @RabbitListener(queuesToDeclare = @Queue(value="loginLogger", durable="true" ))
    public void process2(Message message, Channel channel) throws IOException {
        // 消息处理
        String msg = new String(message.getBody(), Charset.defaultCharset());
        AppLoginLogService appLoginLogService = SpringUtil.getBean("appLoginLogService");
        log.info("接收处理队列当中的消息---登录日志：{}", msg);
        if (StringUtils.isNotEmpty(msg)) {
            AppLoginLog loginLog = JSONObject.parseObject(msg, AppLoginLog.class);
            loginLog.setLoginTime(new Date());
            appLoginLogService.save(loginLog);
            // 向es添加请求日志
            try {
                // 向ES中添加登录日志
                loginLog.setLoginDate(System.currentTimeMillis());
                appLoginLogService.saveToEs(loginLog);
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
            log.info("登录日志入库成功");
        }
    }

    @RabbitListener(queuesToDeclare = @Queue(value="updateLoginLogger", durable="true" ))
    public void process3(Message message, Channel channel) throws IOException {
        // 消息处理
        String msg = new String(message.getBody(), Charset.defaultCharset());
        AppLoginLogService appLoginLogService = SpringUtil.getBean("appLoginLogService");
        log.info("接收处理队列当中的消息---修改登录日志：{}", msg);
        if (StringUtils.isNotEmpty(msg)) {
            QueryFilter filter = new QueryFilter(AppLoginLog.class);
            filter.addFilter("loginId=", msg);
            AppLoginLog loginLog = appLoginLogService.get(filter);
            Date newDate = new Date();
            if (loginLog != null) {
                // 获取时长毫秒数
                Long onlineTime = newDate.getTime() - loginLog.getLoginTime().getTime();
                loginLog.setLogoutTime(newDate);
                loginLog.setOnlineTime(onlineTime.toString());
                appLoginLogService.update(loginLog);
                // 修改es数据
                AppLoginLog loginLogEs = new AppLoginLog();
                loginLogEs.setId(loginLog.getId());
                loginLogEs.setLogoutTime(newDate);
                loginLogEs.setOnlineTime(onlineTime.toString());
                appLoginLogService.updateToEs(loginLogEs);
                log.info("登录日志修改成功");
            }
        }
    }
}
