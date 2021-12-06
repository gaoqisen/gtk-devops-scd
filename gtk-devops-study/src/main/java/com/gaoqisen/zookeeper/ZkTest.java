package com.gaoqisen.zookeeper;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j
public class ZkTest {

    int countSum = 0;

    public static void main(String[] args) throws InterruptedException {

        ZkTest test = new ZkTest();
        for (int i = 0; i < 50; i++) {
            test.testThread("gaoqisen" + i);
        }
    }

    public void testThread(String lockName) {
        final int[] sum = {0};
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 80; i++) {
            executorService.submit(() -> {
                ZkLock lock = ZkLock.build().tryLock(lockName);
                sum[0]++;
                if(sum[0] == 80) {
                    countSum++;
                    System.out.println("锁没有问题: " + countSum);
                }
                lock.unLock();
            });
        }
        executorService.shutdown();
    }

}
