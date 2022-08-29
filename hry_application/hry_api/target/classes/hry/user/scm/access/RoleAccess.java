package hry.user.scm.access;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义注解
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface RoleAccess {
    String roleType();
}
