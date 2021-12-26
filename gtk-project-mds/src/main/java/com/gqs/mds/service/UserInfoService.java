package com.gqs.mds.service;

import com.gqs.mds.entity.UserInfo;
import java.util.List;

/**
 * (UserInfo)表服务接口
 *
 * @author makejava
 * @since 2021-12-10 22:17:50
 */
public interface UserInfoService {


    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    UserInfo update(UserInfo userInfo);

}