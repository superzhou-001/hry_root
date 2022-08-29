/**
 * Copyright:   领航者
 * @author:      liushilei
 * @version:      V1.0
 * @Date:        2015年9月16日 上午11:04:39
 */
package hry.security.jwt.annotation;

import java.lang.annotation.*;

/**
 * 用于些注解的方法，不需要经过身份认证
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UnAuthentication {


}
