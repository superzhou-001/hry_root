package hry.redis.utils;

import hry.redis.DistributedLocker;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yaozh
 * @Description: redis分布式锁帮助类
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
public class LockUtil {
    private static DistributedLocker redissLock;

    public static void setLocker(DistributedLocker locker) {
        redissLock = locker;
    }

    /**
     * 加锁
     * 拿不到lock就不罢休，不然线程就一直block
     * @param lockKey
     * @return
     */
    public static RLock lock(String lockKey) {
        return redissLock.lock(lockKey);
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }

    /**
     * 释放锁
     * @param lock
     */
    public static void unlock(RLock lock) {
        redissLock.unlock(lock);
    }

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。
     * 如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public static RLock lock(String lockKey, int timeout) {
        return redissLock.lock(lockKey, timeout);
    }

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。
     * 如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     * @param lockKey
     * @param unit 时间单位
     * @param timeout 超时时间
     */
    public static RLock lock(String lockKey, TimeUnit unit , int timeout) {
        return redissLock.lock(lockKey, unit, timeout);
    }

    /**
     * 尝试获取锁，在等待时间内获取到锁则返回true,否则返回false,
     * 如果获取到锁，则要么执行完后程序释放锁，要么在给定的超时时间leaseTime后释放锁
     * @param lockKey
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
    }

    /**
     * 尝试获取锁，在等待时间内获取到锁则返回true,否则返回false,
     * 如果获取到锁，则要么执行完后程序释放锁，要么在给定的超时时间leaseTime后释放锁
     * @param lockKey
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, unit, waitTime, leaseTime);
    }


}
