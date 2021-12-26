package com.gqs.lock.dispersed;

/**
 * 数据库实现分布式锁
 */
public class LockDatabase {

    private String lockName;

    /**
     * 获取锁
     *
     * 1. 通过数据库唯一索引插入数据，如果插入成功则获得锁，插入失败则获取锁失败（同步：轮询处理，异步：直接返回结果）
     *
     */
    public synchronized LockDatabase tryLock(String lockName) {
        this.lockName = lockName;
        return this;
    }

    /**
     * 释放锁
     * 1. 直接删除lockName
     */
    public synchronized void unLock() {

    }
}
