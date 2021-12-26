package com.gqs.lock.dispersed;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper实现分布式锁
 * 1. 获取zookeeper链接
 * 2. 获取锁
 *    2.1 获得锁时去创建临时节点(临时节点解决死锁问题：临时节点存在session，如果获取锁的服务断开链接后则临时节点自动删除)（传入【节点创建回调事件】）
 *    2.2 【节点创建回调事件】里面获取所有子节点（传入子【子节点回调事件】）并记录序列节点名
 *    2.3 【子节点回调事件】通过序列是否为第一个节点，不是第一个节点就监听上一个节点的删除事件
 *         2.3.1 通过序列排序
 *         2.3.2 判断当前节点如果是第一个节点则获得锁后写入数据，并打开开关进行具体的业务逻辑执行
 *         2.3.3 如果部署第一个节点，则判断上一个节点是否存在并进行【上一个节点监听事件】
 *    2.4 【上一个节点监听事件】则监听删除事件，如果发生则重复2.2步骤。
 * 3. 释放锁
 *   直接删除临时节点
 */
@Slf4j
public class LockZookeeper implements AsyncCallback.StringCallback , AsyncCallback.Children2Callback, Watcher, Closeable {

    private final CountDownLatch downLatch = new CountDownLatch(1);
    private static final ZooKeeper zooKeeper = InitZookeeper.getZk();
    private String catalog = "/ZKLOCK_";
    private String lockSeqName;
    private String threadName;

    public static LockZookeeper build() {
        return new LockZookeeper();
    }

    /**
     * 同步尝试获得锁
     *
     * ZooDefs.Ids
     * OPEN_ACL_UNSAFE  : 完全开放的ACL，任何连接的客户端都可以操作该属性znode
     * CREATOR_ALL_ACL : 只有创建者才有ACL权限
     * READ_ACL_UNSAFE：只能读取ACL
     *
     * CreateMode
     * PERSISTENT: 持久型
     * PERSISTENT_SEQUENTIAL: 持久顺序型
     * EPHEMERAL: 临时型
     * EPHEMERAL_SEQUENTIAL: 临时顺序型
     * @param lockName 锁名
     */
    public synchronized LockZookeeper tryLock(String lockName) {
        threadName = Thread.currentThread().getName();

        try {
            // 判断锁目录是否存在，不存在则创建
            catalogExistsHandle(lockName);
            zooKeeper.create(catalog.concat("/" + lockName), threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL,this, "create catalog");
            log.info("获取锁 lockName: {}, threadName: {}", lockName, threadName);
            downLatch.await();
        } catch (Exception e) {
            log.error("分布式锁获取失败", e);
        }
        return this;
    }

    /**
     * 目录文件处理
     */
    private synchronized void catalogExistsHandle(String lockName) throws KeeperException, InterruptedException {
        catalog = catalog.concat(lockName);
        Stat exists = zooKeeper.exists(catalog, false);
        if(exists == null) {
            try{
                zooKeeper.create(catalog, threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } catch (KeeperException.NodeExistsException e) {
                log.info("创建的锁节点已经存在，不进行创建: {}", e.getMessage());
            }
        }
    }

    /**
     * 释放锁
     */
    public synchronized void unLock() {
        if(lockSeqName == null) {
            log.info("序列锁名为空，不进行删除操作！");
            return;
        }
        log.info("释放锁 : {}, threadName: {}", lockSeqName, threadName);
        try {
            String lockName = catalog.concat("/" + lockSeqName);
            Stat exists = zooKeeper.exists(lockName, false);
            if(exists != null) {
                zooKeeper.delete(lockName, -1);
            }
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        } catch (KeeperException e) {
            log.error("KeeperException", e);
        }
    }

    @Override
    public void close() {
        unLock();
    }

    /**
     * 节点创建回调事件
     */
    @Override
    public void processResult(int i, String s, Object o, String name) {
        log.info("创建临时序列节点: {}, threadName: {}", name, threadName);
        if(name == null) {
            return;
        }
        zooKeeper.getChildren(catalog, false, this, "get children");
        lockSeqName = name.split(catalog + "/")[1];
    }

    /**
     * 子节点回调事件
     */
    @Override
    public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
        log.info("子节点回调事件 threadName: {}", threadName);
        if(list == null || list.size() < 1) {
            return;
        }

        Collections.sort(list);
        int index = list.indexOf(lockSeqName);
        try {
            if(index < 1) {
                zooKeeper.setData(catalog, threadName.getBytes(), -1);
                log.info("Zookeeper 获取锁完成，开始业务逻辑。threadName: " + threadName);
                downLatch.countDown();
                return;
            }
            String path = catalog + "/" + list.get(index - 1);
            log.info("进行监听上一个回调事件 threadName: {}, path: {}", threadName, path);
            // 去监听上一个节点是否删除，收到删除事件则再次调用回调事件。
            Stat exists = zooKeeper.exists(path, this);
            // 防止监听过程中，其他线程去删除了节点导致无法收到回调事件
            if(exists == null) {
                zooKeeper.setData(catalog, threadName.getBytes(), -1);
                log.info("Zookeeper 前一个已经被删除，直接获取锁。threadName: " + threadName);
                downLatch.countDown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上一个节点监听事件
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("上一个节点监听事件 threadName: {}, watchedEvent: {}, path: {}", threadName,
                watchedEvent.getType().name(), watchedEvent.getPath());
        Event.EventType state = watchedEvent.getType();
        if (state == Event.EventType.NodeDeleted) {
            zooKeeper.getChildren(catalog, false, this, "watch up node");
        }
    }

    /**
     * 获取zookeeper
     */
    @Component
    public static class InitZookeeper{

        private static CountDownLatch downLatch = new CountDownLatch(1);
        private static String address;

        /**
         * 用构造器注入获取dubbo的zookeeper地址
         */
        public InitZookeeper(Environment environment) {
            address = environment.getProperty("dubbo.registry.address");
        }

        /**
         * 获取Zookeeper链接
         */
        public static ZooKeeper getZk() {
            // 创建监听器
            Watcher watcher = watchedEvent -> {
                Event.KeeperState state = watchedEvent.getState();
                switch (state) {
                    case Disconnected:
                        downLatch = new CountDownLatch(1);
                        break;
                    case SyncConnected:
                        downLatch.countDown();
                        break;
                    default:
                        break;
                }
            };

            try {
                int sessionTimeout = 1000;
                log.info("开始创建Zookeeper, address: " + address + ", sessionTimeout: " + sessionTimeout);
                ZooKeeper zooKeeper = new ZooKeeper(address.split("//")[1], sessionTimeout, watcher);
                downLatch.await();
                return zooKeeper;
            } catch (Exception e) {
                log.error("zookeeper创建失败", e);
            }
            return null;
        }
    }
}
