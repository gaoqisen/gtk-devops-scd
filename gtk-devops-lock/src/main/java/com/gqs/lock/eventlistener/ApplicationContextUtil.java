package com.gqs.lock.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil {
    //持有当前容器
    @Autowired
    private ApplicationContext applicationContext;

    public ApplicationContext get() {
        return applicationContext;
    }

}
