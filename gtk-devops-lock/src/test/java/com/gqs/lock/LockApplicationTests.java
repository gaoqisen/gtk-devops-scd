package com.gqs.lock;

import com.gqs.lock.dispersed.LockZookeeper;
import lombok.extern.slf4j.Slf4j;
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

    // 争抢的数据
    int sum = 0;

    // 循环的数量
    int forCount = 10;

    // 线程数量
    int threadCount = 1000;

    CountDownLatch count = new CountDownLatch(forCount * threadCount);
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
        Assertions.assertEquals(sum, forCount * threadCount);
        log.info("分布式锁 threadCount: {}, forCount: {}, sum: {}, 耗时: {}s", threadCount, forCount, sum, (System.currentTimeMillis() - startTime)/1000);
    }


    public void testThread(String lockName) {
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                LockZookeeper lock = LockZookeeper.build().tryLock(lockName);
                // RedisLock lock = RedisLock.build().tryLock(lockName);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sum++;
                if(sum % threadCount == 0) {
                    System.out.println("锁没有问题: " + sum);
                }
                count.countDown();
                lock.unLock();

            });
        }
    }


    @Test
    void testThread() throws InterruptedException {
        for (int i = 0; i < threadCount*forCount; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sum++;
                count.countDown();
            });
        }
        count.await();
        Assertions.assertEquals(threadCount*forCount, sum);
    }

}
