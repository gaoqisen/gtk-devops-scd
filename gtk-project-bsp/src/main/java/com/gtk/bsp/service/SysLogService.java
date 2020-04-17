

package com.gtk.bsp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gtk.bsp.entity.SysLogEntity;
import com.gtk.bsp.utils.PageUtils;

import java.util.Map;


/**
 * 系统日志
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
