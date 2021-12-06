package com.gqs.lock.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PropertiesUtil {

    @Autowired
    private Environment environment;
    private static Environment env;

    /**
     * 给static注入方案一：
     */
    //PostConstruct注解不可以有参数的
    @PostConstruct
    public void init(){
        env = this.environment;
    }

    public static String getDubboAddress(){
        String type = env.getProperty("dubbo.registry.address");
        return type;
    }

    @Component
    public static class InitEnvironment{

        private Environment environment;

        public InitEnvironment(Environment environment) {
            this.environment = environment;
        }

        public Environment getEnv() {
            return environment;
        }

    }


}
