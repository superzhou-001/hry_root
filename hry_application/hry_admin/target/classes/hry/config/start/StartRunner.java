package hry.config.start;

import hry.security.logger.ReflectRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("-------------------------------程序启动controller安全检查------------------------------");
        ReflectRequestUtil.findRequest();

        log.info("-------------------------------初始化XXXXXXXXXX------------------------------");

    }
}
