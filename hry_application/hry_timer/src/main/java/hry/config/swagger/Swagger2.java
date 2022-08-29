package hry.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import hry.util.SwaggerDocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 使用swagger2
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Slf4j
public class Swagger2 {
    @Bean
    public Docket createManageApi() {
        return SwaggerDocket.getDocket("基础平台-后台接口","基础平台-后台接口","刘诗垒","1.0","hry");
    }
}
