package com.gtk.spring.security.oauth2.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OAuth2ServerApplicationTests {
    @Test
    public void testBCryptPasswordEncoder() {
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
    }

    @Test
    public void test() {
        // 定义一个数组
        String[] array = {
                "a",
                "b",
                "c",
                "d"
        };

        // 转换成集合
        List<String> acgs = Arrays.asList(array);
        // 传统的遍历方式
        System.out.println("传统的遍历方式：");
        for (String acg : acgs) {
            System.out.println(acg);
        }
        // 使用 Lambda 表达式以及函数操作(functional operation)
        acgs.forEach((acg) -> System.out.println(acg));


        System.out.println("__________");
        acgs.forEach(System.out::println);

    }

}
