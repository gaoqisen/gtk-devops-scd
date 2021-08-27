package com.gqs.mds.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

@Configuration
@EnableAsync
public class TaskPoolConfig {

    /**
     * 利用TtlExecutors装饰线程池
     */
    @Bean("taskExecutor")
    public ExecutorService taskExecutro(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.initialize();
        return TtlExecutors.getTtlExecutorService(taskExecutor.getThreadPoolExecutor());
    }

}
