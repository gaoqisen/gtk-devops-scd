package com.gtk.spring.security.oauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;
// RBAC 基于角色的权限控制
@SpringBootApplication
@MapperScan(basePackages = "com.gtk.spring.security.oauth2.server.mapper")
public class OAuth2ServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(OAuth2ServerApplication.class, args);
    }

}
