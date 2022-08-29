package hry.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import hry.business.cf.model.CfFacilityMortgage;
import hry.business.cu.model.CuHouse;
import hry.platform.newuser.model.NewAppUser;
import hry.util.SwaggerDocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用swagger2
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Slf4j
public class Swagger2 {
    private final TypeResolver typeResolver;

    @Autowired
    public Swagger2(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket createManageApi() {
        //return SwaggerDocket.getDocket("web前台接口","web前台接口","刘诗垒","1.0","hry");
        return addModel(SwaggerDocket.getDocket("1.基础平台接口-无需登录","hry.platform"));
    }

    @Bean
    @Order(value = 1)
    public Docket scmApi() {
        return addModel(SwaggerDocket.getDocket("2.供应链接口-无需登录","hry.scm"));
    }

    @Bean
    @Order(value = 2)
    public Docket userPlatFormApi() {
        return addModel(SwaggerDocket.getDocket("3.基础平台接口-需要登录","hry.user.platform"));
    }

    @Bean
    @Order(value = 3)
    public Docket userScmApi() {
        return addModel(SwaggerDocket.getDocket("4.供应链接口-需要登录","hry.user.scm"));
    }

    @Bean
    @Order(value = 4)
    public Docket helpLoanApi() {
        return addModel(SwaggerDocket.getDocket("5.助贷接口-无需登录","hry.helpLoan"));
    }
    /**
     * 添加Model类型
     * @param docket
     * @return
     */
    private Docket addModel(Docket docket){
        //添加自定义Model类型
        docket.additionalModels(typeResolver.resolve(NewAppUser.class));
        docket.additionalModels(typeResolver.resolve(CfFacilityMortgage.class));
        return docket;
    }
}
