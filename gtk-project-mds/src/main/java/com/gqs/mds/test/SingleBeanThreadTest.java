package com.gqs.mds.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spring")
public class SingleBeanThreadTest {

    @Autowired
    private SpringDefaultBean springDefaultBean;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public void test() {
        springDefaultBean.setValue("555");
        System.out.println("处理前获取的值" + springDefaultBean.getValue());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("睡眠后获取的值" + springDefaultBean.getValue());
    }

    @RequestMapping(value = "set", method = RequestMethod.GET)
    public void set() {
        springDefaultBean.setValue("777");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("重新设置的值" + springDefaultBean.getValue());
    }
}
