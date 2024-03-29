package com.gqs.lock.controller;

import com.gqs.lock.dispersed.LockRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("zookeeper")
    public void zookeeper() throws InterruptedException {
        LockRedis zkLock = LockRedis.build().tryLock("zkLocktTest");
        Thread.sleep(2000);
        zkLock.unLock();
    }


}
