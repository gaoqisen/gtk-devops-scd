package com.gaoqisen.demo;

import com.gaoqisen.zookeeper.ZkLock;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("测试类")
class GtkDevopsStudyApplicationTests {

    @Test
    @DisplayName("测试断言")
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            new Thread(){
                @SneakyThrows
                @Override
                public void run() {

                    String lockName = "gaoqisen";

                    ZkLock.build().tryLock(lockName);
                    Thread.sleep(1000);
                    ZkLock.build().unLock();
                }
            }.start();
        }
    }

}
