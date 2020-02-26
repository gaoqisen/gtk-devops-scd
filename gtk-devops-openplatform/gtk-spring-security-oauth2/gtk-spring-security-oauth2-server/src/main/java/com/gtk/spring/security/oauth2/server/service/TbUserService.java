package com.gtk.spring.security.oauth2.server.service;

import com.gtk.spring.security.oauth2.server.domain.TbUser;

public interface TbUserService{

    public TbUser getByUsername(String username);

}
