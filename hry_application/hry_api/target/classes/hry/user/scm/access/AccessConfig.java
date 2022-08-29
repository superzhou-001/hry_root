package hry.user.scm.access;

import hry.security.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class AccessConfig implements WebMvcConfigurer {

    @Bean
    AccessInterceptor accessInterceptor(){
        return new AccessInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("****************【系统启动中】配置拦截器****************");
        //管理端token拦截器
        registry.addInterceptor(accessInterceptor()).addPathPatterns("/**");
    }
}
