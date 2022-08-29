package hry.redis.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: yaozh
 * @Description:
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
@Aspect
@Component
@Slf4j
public class RLockerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedissonClient redissonClient;

    //这里需要修改对应的包名
    @Pointcut("@annotation(hry.redis.aop.RLocker)")
    public void rLockAspect() {
    }

    @Around("rLockAspect()")
    public Object arround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object object = null;
        RLock lock = null;
        log.info("rlockAspect start ");
        try {
            RLocker rlockInfo = getRLockInfo(proceedingJoinPoint);
            String lockKey = getLocalKey(proceedingJoinPoint, rlockInfo);
            lock = redissonClient.getLock(lockKey);
            if (lock != null) {
                final boolean status = lock.tryLock(rlockInfo.releaseTime(), rlockInfo.timeUnit());
                if (status) {
                    object = proceedingJoinPoint.proceed();
                }
            } else {
                log.info("未获取到锁：{}", lockKey);
            }
        } finally {
            // 当前线程获取到锁再释放锁
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return object;
    }
/*
    *//**
     * @description  在连接点执行之前执行的通知
     *//*
    @Before("rLockAspect()")
    public void doBeforeGame(){
        System.out.println("在连接点执行之前执行的通知");
    }

    *//**
     * @description  在连接点执行之后执行的通知（返回通知和异常通知的异常）
     *//*
    @After("rLockAspect()")
    public void doAfterGame(){
        System.out.println("在连接点执行之后执行的通知（返回通知和异常通知的异常）");
    }*/

    public RLocker getRLockInfo(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(RLocker.class);
    }

    /**
     * 获取redis lock key
     *
     * @param proceedingJoinPoint
     * @return
     */
    public String getLocalKey(ProceedingJoinPoint proceedingJoinPoint, RLocker rlockInfo) {
        StringBuilder localKey = new StringBuilder("Rlock");
        String businessNo = "";
        // 如果没有设置锁值
        if (StringUtils.isNotEmpty(rlockInfo.lockName())) {
            businessNo = rlockInfo.lockName();
        } else {
            //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
            logger.info("################CLASS_METHOD : " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName());
            String methodName = proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName();
            businessNo = methodName;
        }
        return localKey+":"+businessNo;
    }

}
