package com.gqs.mds.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.gqs.mds.entity.SysDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 动态数据源，用于将dataSource提供给spring
 * 由于AbstractRoutingDataSource没有set单个数据源和获取全部数据源的方法
 * 因此只能在自己实现的DynamicDataSource里面存储所有的数据源，每次set单个数据源的时候就汇总后赋值
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final Map<Object, Object> dynamicDataSources = new HashMap<>();

    /**
     * 获取数据源编号用TransmittableThreadLocal实现，可以在线程池里面动态切换数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getVal();
    }

    /**
     * 设置目标数据源，为了解决AbstractRoutingDataSource没有汇总数据源的问题
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        Set<Map.Entry<Object, Object>> entries = targetDataSources.entrySet();
        for (Map.Entry<Object, Object> map : entries) {
            if(dynamicDataSources.containsKey(map.getKey())) {
                continue;
            }
            dynamicDataSources.put(map.getKey(), map.getValue());
        }
        super.setTargetDataSources(dynamicDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 提供了一个设置单个数据源的方法，简化调用方
     */
    public void setDataSource(String dataSourceNo, SysDataSource dataSource) {
        Map<Object, Object> map = new HashMap<>();
        map.put(dataSourceNo, getDruidDataSource(dataSource));
        setTargetDataSources(map);
    }

    /**
     * 阿里druid数据源的转换，转换的时候测试数据源是否正常使用，还有很多的参数可以在这里设置
     */
    public static DruidDataSource getDruidDataSource(SysDataSource source) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(source.getUrl());
        druidDataSource.setUsername(source.getUsername());
        druidDataSource.setPassword(source.getPassword());
        druidDataSource.setDriverClassName(source.getDriverClassName());
        try{
            druidDataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("数据库连接异常", e);
        }
        return druidDataSource;
    }
}