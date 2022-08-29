package hry.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FilterConfig {


    @Bean
    FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();

        log.info("****************【系统启动中】配置跨域过滤器****************");
        //跨域过滤器
        registration.setFilter(accessOriginFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);

        log.info("****************【系统启动中】配置JWT过滤器****************");
        registration.setFilter(jwtFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(2);

        return registration;
    }

    @Bean
    JWTFilter jwtFilter(){
        return new JWTFilter();
    }

    @Bean
    AccessOriginFilter accessOriginFilter(){
        return new AccessOriginFilter();
    }

}
