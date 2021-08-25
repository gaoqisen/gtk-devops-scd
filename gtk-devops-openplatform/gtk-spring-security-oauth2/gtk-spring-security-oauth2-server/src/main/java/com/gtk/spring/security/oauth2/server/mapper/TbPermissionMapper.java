package com.gtk.spring.security.oauth2.server.mapper;

import com.gtk.spring.security.oauth2.server.domain.TbPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbPermissionMapper extends Mapper<TbPermission> {
    List<TbPermission> selectByUserId(@Param("id") Long id);
}