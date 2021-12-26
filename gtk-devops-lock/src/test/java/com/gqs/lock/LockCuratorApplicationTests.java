package com.gqs.lock;

import com.gqs.lock.dispersed.LockCurator;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
class LockCuratorApplicationTests {


    int countSum = 0;
    int forCount = 50;
    int threadCount = 80;
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
                LockCurator lock = LockCurator.build().tryLock(lockName);
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


    @Test
    public void sharedLock() throws Exception {

        // 设置 ZooKeeper 服务地址为本机的 2181 端口
        connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        // 重试策略
        // 初始休眠时间为 1000ms, 最大重试次数为 3
        retry = new ExponentialBackoffRetry(1000, 3);
        // 创建一个客户端, 60000(ms)为 session 超时时间, 15000(ms)为链接超时时间
        client = CuratorFrameworkFactory.newClient(connectString, 60000, 15000, retry);
        client2 = CuratorFrameworkFactory.newClient(connectString, 60000, 15000, retry);
        // 创建会话
        client.start();
        client2.start();

        // 创建共享锁
        final InterProcessLock lock = new InterProcessMutex(client, lockPath);
        // lock2 用于模拟其他客户端
        final InterProcessLock lock2 = new InterProcessMutex(client2, lockPath);

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取锁对象
                try {
                    lock.acquire();
                    System.out.println("1获取锁===============");
                    // 测试锁重入
                    lock.acquire();
                    System.out.println("1再次获取锁===============");
                    Thread.sleep(5 * 1000);
                    lock.release();
                    System.out.println("1释放锁===============");
                    lock.release();
                    System.out.println("1再次释放锁===============");

                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取锁对象
                try {
                    lock2.acquire();
                    System.out.println("2获取锁===============");
                    // 测试锁重入
                    lock2.acquire();
                    System.out.println("2再次获取锁===============");
                    Thread.sleep(5 * 1000);
                    lock2.release();
                    System.out.println("2释放锁===============");
                    lock2.release();
                    System.out.println("2再次释放锁===============");

                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        countDownLatch.await();
        CloseableUtils.closeQuietly(client);

    }

    // ZooKeeper 锁节点路径, 分布式锁的相关操作都是在这个节点上进行
    private final String lockPath = "/distributed-lock";

    // ZooKeeper 服务地址, 单机格式为:(127.0.0.1:2181),
    // 集群格式为:(127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183)
    private String connectString;
    // Curator 客户端重试策略
    private RetryPolicy retry;
    // Curator 客户端对象
    private CuratorFramework client;
    // client2 用户模拟其他客户端
    private CuratorFramework client2;


}
