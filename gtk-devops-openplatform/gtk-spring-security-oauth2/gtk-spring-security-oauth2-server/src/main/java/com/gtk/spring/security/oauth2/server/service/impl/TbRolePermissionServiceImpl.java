package com.gtk.spring.security.oauth2.server.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.gtk.spring.security.oauth2.server.mapper.TbRolePermissionMapper;
import com.gtk.spring.security.oauth2.server.service.TbRolePermissionService;
@Service
public class TbRolePermissionServiceImpl implements TbRolePermissionService{

    @Resource
    private TbRolePermissionMapper tbRolePermissionMapper;

}
