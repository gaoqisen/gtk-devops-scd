package com.gqs.mds.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.gqs.mds.dao.SysDataSourceDao;
import com.gqs.mds.entity.SysDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataSourceInitialize implements CommandLineRunner {

    @Resource
    private DynamicDataSource dynamicDataSource;

    @Resource
    private SysDataSourceDao sysDataSourceDao;

    /**
     * 有效标识
     */
    private final static String VALID_TAG_STATUS = "1";

    /**
     * 在初始化项目的时候将数据库里面的数据源加载到动态数据源里面
     */
    @Override
    public void run(String... args) throws Exception {
        SysDataSource param = new SysDataSource();
        param.setStatus(VALID_TAG_STATUS);
        List<SysDataSource> sysDataSources = sysDataSourceDao.queryAll(param);

        Map<Object, Object> map = new HashMap<>(sysDataSources.size());
        for (SysDataSource source : sysDataSources) {
            if(map.containsKey(source.getDataSourceNo())) {
                continue;
            }
            DruidDataSource druidDataSource = DynamicDataSource.getDruidDataSource(source);
            map.put(source.getDataSourceNo(), druidDataSource);
        }

        dynamicDataSource.setTargetDataSources(map);
    }
}
