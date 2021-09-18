package com.gqs.mds.test;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * spring默认注入的bean是单例的bean，如果使用有状态的bean是则是程不安全的。
 * bean中的属性，基本类型都属于有状态的（有存储数据能力）
 * bean中的属性，无状态的对象是属于无状态的（无存储数据能力）
 *
 * singleton: 默认的单例bean，spring启动的时候实例化一次。线程不安全
 * prototype: 原型模式，线程每次调用的时候都会实例化。线程安全
 * request: 在每一个request里面都会实例化一次
 * session: 在每一个session里面都会实例化一次
 * globalSession: 全局session，只在porlet的web程序中使用，普通web项目使用和session一样
 *
 */
@Component
@Scope("singleton")
public class SpringDefaultBean {

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
