package com.gqs.mds.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configurable
@Component
public class DataSourceConfig {

    private DataSource dataSource;

    private DynamicDataSource dynamicDataSource;

    /**
     * 默认数据源,用来获取配置在yml上面的数据
     */
    @Primary
    @Bean("dataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource getDataSource(){
        dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    /**
     * 将DynamicDataSource交给spring管理，用于在其他地方也可以方便的set数据源
     */
    @Bean
    @DependsOn("dataSource")
    public DynamicDataSource dynamicDataSource(){
        DynamicDataSource dds= new DynamicDataSource();
        dds.setDefaultTargetDataSource(dataSource);
        Map<Object, Object> map = new HashMap<>();
        map.put("default", dataSource);
        dds.setTargetDataSources(map);
        dds.afterPropertiesSet();
        dynamicDataSource = dds;
        return dds;
    }

    /**
     * 实例化myBatis的SqlSessionFactor，在这里去解析mapper的xml文件
     */
    @Bean
    @DependsOn("dynamicDataSource")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

}
