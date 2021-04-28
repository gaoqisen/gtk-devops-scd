package com.gaoqisen.demo;

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
        // 断言
        Assert.assertEquals(true, false);
    }

}
