package com.gtk.spring.security.oauth2.resource.service;

import com.gtk.spring.security.oauth2.resource.domain.AdminUser;

import java.util.List;

public interface AdminUserService{

    public List<AdminUser> findAll();

}
