package com.gqs.lock.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 给静态属性注入bean
 */
@Component
public class PropertiesUtil {

    /**
     * 方案一：
     * 利用PostConstruct将普通属性值赋给静态成员
     *
     * PostConstruct注解不可以有参数的
     */
    @Autowired
    private Environment environment;
    private static Environment envOne;


    @PostConstruct
    public void init(){
        envOne = this.environment;
    }


    /**
     * 方案二：
     * 静态类直接用构造器注入方式给静态属性赋值
     */
    private static Environment envTwo;

    @Component
    public static class InitEnvironment{
        public InitEnvironment(Environment environment) {
            envTwo = environment;
        }
    }
}
