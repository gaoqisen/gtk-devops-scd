

package com.gtk.bsp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gtk.bsp.entity.SysUserTokenEntity;
import com.gtk.bsp.utils.Result;

/**
 * 用户Token
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	Result createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
