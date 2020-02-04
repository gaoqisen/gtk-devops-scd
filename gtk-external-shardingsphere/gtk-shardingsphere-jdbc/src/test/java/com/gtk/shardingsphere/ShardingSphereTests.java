package com.gtk.shardingsphere;

import com.gtk.shardingsphere.domain.AdminUser;
import com.gtk.shardingsphere.mapper.AdminUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingSphereTests {
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Test
    public void testInsertOrder() {
        for(int i = 0; i< 20; i++) {
            AdminUser adminUser = new AdminUser();
            adminUser.setId(i);
            adminUser.setRole(1);
            adminUserMapper.insert(adminUser);
        }

    }
    @Test
    public void testSelectAll() {
        List<AdminUser> tbOrders = adminUserMapper.selectAll();
        tbOrders.forEach(tbOrder -> {
            System.out.println(tbOrder);
        });
    }
}