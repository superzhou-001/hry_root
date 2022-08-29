package hry.security.logger;


import java.lang.annotation.*;

/**
 *  @author: liuchenghui
 *  @Date: 2020/4/10 15:16
 *  @Description: controller日志注解
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ControllerLogger {

}


