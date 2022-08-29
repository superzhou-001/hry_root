package hry.redis.aop;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yaozh
 * @Description: 分布式锁注解
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
/**
 * @description 权限验证的注解类
 *
 * @Inherited：在使用此自定义注解时，如果注解在类上面时，子类会自动继承此注解，否则，子类不会继承此注解。这里一定要记住，使用Inherited声明出来的注解，只有在类上使用时才会有效，对方法，属性等其他无效。
 * @Target：表示此注解可以放置的位置。常见的位置有：TYPE=枚举或注解上，FIELD=字段上，METHOD=方法上，PARAMETER=函数形参列表中，CONSTRUCTOR=构造函数上，LOCAL_VARIABLE=局部变量上                                                                                                        等等其他位置。
 * @Retention：此注解的生命周期。常见的有：SOURCE=源码时期；CLASS=字节码时期（已编译）；RUNTIME=运行时期，通常是用这个的时候要多。
 * @Documentd：生成注解文档。
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RLocker {
    //锁名称
    String lockName() default "";

    //释放时间
    long releaseTime() default 5*1000;

    //时间单位
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
