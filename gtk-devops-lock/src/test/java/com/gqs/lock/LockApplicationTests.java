package com.gqs.lock;

import com.gqs.lock.config.RedisLock;
import com.gqs.lock.config.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
class LockApplicationTests {

    int countSum = 0;
    int forCount = 5;
    int threadCount = 5;
    CountDownLatch count = new CountDownLatch(1);
    ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Test
    @DisplayName("分布式锁：测试样例")
    void contextLoads() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < forCount; i++) {
            testThread("gaoqisen" + i);
        }
        count.await();
        executorService.shutdown();
        Assertions.assertEquals(forCount, countSum);
        log.info("分布式锁 threadCount: {}, forCount: {}, countSum: {}, 耗时: {}s", threadCount, forCount, countSum, (System.currentTimeMillis() - startTime)/1000);
    }


    public void testThread(String lockName) {
        final int[] sum = {0};
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                ZkLock lock = ZkLock.build().tryLock(lockName);
                // RedisLock lock = RedisLock.build().tryLock(lockName);
                sum[0]++;
                if(sum[0] == threadCount) {
                    countSum++;
                    if(countSum == forCount) {
                        count.countDown();
                    }
                    System.out.println("锁没有问题: " + countSum);
                }
                lock.unLock();
            });
        }
    }

}
