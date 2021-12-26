package com.gqs.mds.service.impl;

import com.gqs.mds.entity.UserInfo;
import com.gqs.mds.dao.UserInfoDao;
import com.gqs.mds.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-12-10 22:17:51
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;



    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo update(UserInfo userInfo) {

        UserInfo userInfo1 = new UserInfo();
        this.userInfoDao.update("123456789");
        System.out.println(userInfo1.toString());


        UserInfo params = new UserInfo();
        params.setId("1");
        UserInfo info = this.userInfoDao.queryOne(params);
        System.out.println(info.toString());
        return null;
    }
}