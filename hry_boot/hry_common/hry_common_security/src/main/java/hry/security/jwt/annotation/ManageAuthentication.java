/**
 * Copyright:   领航者
 * @author:      liushilei
 * @version:      V1.0
 * @Date:        2015年9月16日 上午11:04:39
 */
package hry.security.jwt.annotation;

import java.lang.annotation.*;

/**
 * <p> 自定义注解，记录一下当前方法的中文名字</p>
 * @author         liushilei
 * @Date           2015年9月16日 上午11:04:39
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ManageAuthentication {


}
