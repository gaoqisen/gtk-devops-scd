package com.gtk.common.config;

import java.util.concurrent.Executor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

import static java.util.concurrent.Executors.*;

/**
 * 定时执行
 */
@Configuration
public class ScheduledConfig implements SchedulingConfigurer{
	@Value("${schedule.start}")
	private String scheduleStart;
	@Value("${schedule.class}")
	private String scheduleClasses;
	
	@Autowired
    private ApplicationContext context;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    	if("1".equals(scheduleStart) && !scheduleClasses.isEmpty()) {
    		String [] classArr = scheduleClasses.split(",");
    		for(String s : classArr) {
    			Class<?> clazz;
                Object task;
                try {
                    clazz = Class.forName(s);
                    task = context.getBean(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("配置文件中schedule.class的" + s + "有误", e);
                } catch (BeansException e) {
                    throw new IllegalArgumentException(s + "未纳入到spring管理", e);
                }
                Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
                taskRegistrar.addTriggerTask(((Runnable) task),
                    triggerContext -> {
                        return new CronTrigger("0/5 * * * * ?").nextExecutionTime(triggerContext);
                    }
                );
    		}
    	}

    }
    @Bean
    public Executor taskExecutor() {
        return newScheduledThreadPool(10);
    }
}