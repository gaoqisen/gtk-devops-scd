

package com.gtk.bsp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gtk.bsp.entity.SysRoleEntity;
import com.gtk.bsp.utils.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * 角色
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}