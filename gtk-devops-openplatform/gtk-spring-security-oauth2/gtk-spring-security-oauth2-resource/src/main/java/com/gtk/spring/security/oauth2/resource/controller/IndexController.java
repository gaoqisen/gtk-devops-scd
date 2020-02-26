package com.gtk.spring.security.oauth2.resource.controller;

import com.gtk.spring.security.oauth2.resource.domain.AdminUser;
import com.gtk.spring.security.oauth2.resource.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping("/index")
    public List<AdminUser> index() {
        return adminUserService.findAll();
    }

}
