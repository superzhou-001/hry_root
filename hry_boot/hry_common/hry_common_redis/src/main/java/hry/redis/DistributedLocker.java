package hry.redis;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yaozh
 * @Description:
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
public interface DistributedLocker {

    /**
     * 加锁 拿不到lock就不罢休，不然线程就一直block
     * @param lockKey
     * @return
     */
    RLock lock(String lockKey);

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。
     * 如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    RLock lock(String lockKey, long timeout);

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。
     * 如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     * @param lockKey
     * @param unit 时间单位
     * @param timeout 超时时间
     */
    RLock lock(String lockKey, TimeUnit unit, long timeout);

    /**
     * 尝试获取锁，在等待时间内获取到锁则返回true,否则返回false,
     * 如果获取到锁，则要么执行完后程序释放锁，要么在给定的超时时间leaseTime后释放锁
     * @param lockKey
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    /**
     * 释放锁
     * @param lockKey
     */
    void unlock(String lockKey);

    /**
     * 释放锁
     * @param lock
     */
    void unlock(RLock lock);
}
