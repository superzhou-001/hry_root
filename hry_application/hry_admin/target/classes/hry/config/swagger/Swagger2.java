package hry.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import hry.util.SwaggerDocket;
import hry.util.properties.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
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
        return SwaggerDocket.getDocket("基础平台-后台接口","基础平台-后台接口","刘诗垒","1.0","hry.platform");
    }

    @Bean
    @Order(value = 1)
    public Docket cuDocket() {
        //
        return SwaggerDocket.getDocket("客户菜单-后台接口","客户中心-后台接口","刘诗垒","1.0","hry.business.manage");
    }
    @Bean
    @Order(value = 2)
    public Docket qccDocket() {
        //
        return SwaggerDocket.getDocket("企查查接口","企查查接口","刘诗垒","1.0","hry.business.qcc");
    }
    @Bean
    @Order(value = 3)
    public Docket ctDocket() {
        //
        return SwaggerDocket.getDocket("合同管理接口","合同管理接口","刘诗垒","1.0","hry.business.ct");
    }
    @Bean
    @Order(value = 4)
    public Docket ssDocket() {
        //
        return SwaggerDocket.getDocket("授信管理接口","授信管理接口","刘诗垒","1.0","hry.business.cf");
    }
    @Bean
    @Order(value = 5)
    public Docket scmDocket() {
        //
        return SwaggerDocket.getDocket("供应链接口","供应链接口","刘诗垒","1.0","hry.scm");
    }
    @Bean
    @Order(value = 6)
    public Docket faDocket() {
        //
        return SwaggerDocket.getDocket("保理接口","保理接口","刘诗垒","1.0","hry.business.fa");
    }
    @Bean
    @Order(value = 7)
    public Docket helpLoanDocket() {
        //
        return SwaggerDocket.getDocket("助贷接口","助贷接口","刘诗垒","1.0","hry.helpLoan");
    }
}
