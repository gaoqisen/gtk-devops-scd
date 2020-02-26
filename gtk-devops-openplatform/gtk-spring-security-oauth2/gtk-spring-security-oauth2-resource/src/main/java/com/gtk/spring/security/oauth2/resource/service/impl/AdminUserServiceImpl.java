package com.gtk.spring.security.oauth2.resource.service.impl;

import com.gtk.spring.security.oauth2.resource.domain.AdminUser;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.gtk.spring.security.oauth2.resource.mapper.AdminUserMapper;
import com.gtk.spring.security.oauth2.resource.service.AdminUserService;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService{

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public List<AdminUser> findAll() {
        return adminUserMapper.selectAll();
    }
}
