package com.gqs.lock.dispersed;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Closeable;

/**
 * Curator基于zookeeper实现分布式锁
 */
@Slf4j
public class LockCurator implements Closeable {

    private InterProcessMutex interProcessMutex;

    /**
     * CuratorFramework instances are fully thread-safe. You should share one CuratorFramework per ZooKeeper cluster in your application.
     * 利用静态类初始化一份client
     */
    private static CuratorFramework client;

    public static LockCurator build() {
        return new LockCurator();
    }

    /**
     * 同步获取锁
     *
     * InterProcessMutex：分布式可重入排它锁
     * InterProcessSemaphoreMutex：分布式排它锁 不可重入
     * InterProcessReadWriteLock：分布式读写锁
     * InterProcessMultiLock：将多个锁作为单个实体管理的容器
     *
     */
    public synchronized LockCurator tryLock(String lockName) {
        log.info("同步获取锁 lockName: {}", lockName);
        try {
            String prefix = "/bs_";
            final InterProcessMutex lock = new InterProcessMutex(client, prefix.concat(lockName));
            lock.acquire();
            interProcessMutex = lock;
            return this;
        } catch (Exception e) {
            log.info("同步获取锁", e);
            throw new RuntimeException("Curator锁创建失败");
        }
    }


    /**
     * 释放锁
     */
    public synchronized void unLock() {
        log.info("释放锁!");
        try{
            interProcessMutex.release();
        } catch (Exception e) {
            throw new RuntimeException("Curator锁释放失败");
        }
    }

    @Override
    public void close(){
        unLock();
    }


    @Component
    public static class InitAddress{

        public InitAddress(Environment environment) {
            String dubboAddress = environment.getProperty("dubbo.registry.address");
            assert dubboAddress != null;
            String address = dubboAddress.split("//")[1];
            // Curator 客户端重试策略 初始休眠时间为 1000ms, 最大重试次数为 3
            RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
            // 创建一个客户端, 60000(ms)为 session 超时时间, 15000(ms)为链接超时时间
            client = CuratorFrameworkFactory.newClient(address, 60000, 15000, retry);
            // 创建会话
            client.start();
        }
    }

}
