package hry.config.timers;

import hry.platform.newuser.service.AppLoginLogService;
import hry.util.SpringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *  @author: liuchenghui
 *  @Date: 2020/3/24 11:54
 *  @Description: 初始配置类定时器
 *  EnableScheduling：开启定时器
 *  EnableAsync：开启异步模式
 */
//  Cron表达式范例
//  每隔5秒执行一次：   */5 * * * * ?
//  每隔1分钟执行一次：  0 */1 * * * ?
//  每天23点执行一次：  0 0 23 * * ?
//  每天凌晨1点执行一次： 0 0 1 * * ?
//  每月1号凌晨1点执行一次：   0 0 1 1 * ?
//  每月最后一天23点执行一次：  0 0 23 L * ?
//  每周星期天凌晨1点实行一次：  0 0 1 ? * L
//  在26分、29分、33分执行一次：   0 26,29,33 * * * ?
//  每天的0点、13点、18点、21点都执行一次： 0 0 0,13,18,21 * * ?
//  每隔5分钟执行一次：    0 0/5 * * * ?
@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleTimer {

    /**
     * 初始化网站参数配置
     * Async 异步
     */
    @Async
    @Scheduled(cron = "* * * * 1 ?")
    public void initSystemConfig() {
        System.out.println("定时器的配置");
    }

    /**
     * 定时统计离线用户的登录时长
     * Async 异步
     */
    @Async
    @Scheduled(cron = "*/5 * * * * ?")
    public void updateLoginLog() {
        AppLoginLogService appLoginLogService = SpringUtil.getBean("appLoginLogService");
        appLoginLogService.updateLoginLogTimer();
    }


}
