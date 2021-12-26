package com.gqs.lock.dispersed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LockRedis {

    private static StringRedisTemplate template;

    private String lockName;



    public static LockRedis build() {
        return new LockRedis();
    }

    /**
     * 异步锁，如果锁存在则返回false
     *
     * @param lockName 锁名称
     */
    public boolean tryAsyncLock(String lockName) {
        this.lockName = lockName;
        long timeout = 50;
        Boolean lock = template.opsForValue().setIfAbsent(lockName, "lock", timeout, TimeUnit.SECONDS);
        if(lock == null) {
            return false;
        }
        return lock;
    }

    /**
     * 同步锁
     *
     * @param lockName 锁名称
     */
    public LockRedis tryLock(String lockName) {
        while (true) {
            if(tryAsyncLock(lockName)) {
                log.info("{}获得锁，开始执行业务逻辑！", lockName);
                break;
            }
            try {
                log.info("{}没有获得锁，等了轮询获取！", lockName);
                long pollTime = 2;
                TimeUnit.SECONDS.sleep(pollTime);
            } catch (InterruptedException e) {
                log.info("同步锁获取，轮询等待sleep失败", e);
            }
        }
        return this;
    }

    public void unLock() {
        if(lockName == null || lockName.isEmpty()) {
            return;
        }
        log.info("释放锁 lockName: {}", lockName);
        template.delete(lockName);
    }

    /**
     * 利用构造器注入静态属性
     */
    @Component
    public static class InitRedisTemplate{

        public InitRedisTemplate(StringRedisTemplate stringRedisTemplate) {
            template = stringRedisTemplate;
        }
    }

}
