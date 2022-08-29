package hry.config.timers;

import hry.scm.project.service.MortgageProjectService;
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
 *
 *  EnableScheduling：开启定时器
 *  EnableAsync：开启异步模式
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleScmTimer {

    /**
     * 修改代采质押项目项目状态
     * Async 异步
     */
    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateMortgageStatus() {
        MortgageProjectService mortgageProjectService = SpringUtil.getBean("mortgageProjectService");
        mortgageProjectService.updateMortgageStatus();
    }


}
