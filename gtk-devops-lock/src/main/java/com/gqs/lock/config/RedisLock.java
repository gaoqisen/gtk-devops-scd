package com.gqs.lock.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisLock {

    private static final StringRedisTemplate template = InitRedisTemplate.getStringRedisTemplate();

    private String lockName;



    public static RedisLock build() {
        return new RedisLock();
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
    public RedisLock tryLock(String lockName) {
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

    @Component
    public static class InitRedisTemplate{

        private static StringRedisTemplate stringRedisTemplate;

        public InitRedisTemplate(StringRedisTemplate template) {
            stringRedisTemplate = template;
        }

        public static StringRedisTemplate getStringRedisTemplate() {
            return stringRedisTemplate;
        }

    }

}
