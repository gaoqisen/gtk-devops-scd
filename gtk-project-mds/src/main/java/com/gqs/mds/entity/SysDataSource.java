package com.gqs.mds.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 数据源(SysDataSource)实体类
 *
 * @author makejava
 * @since 2021-08-26 18:13:33
 */
public class SysDataSource implements Serializable {
    private static final long serialVersionUID = 427489915027814469L;
    
    private Long id;
    /**
    * 数据源编码
    */
    private String dataSourceNo;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 名称
    */
    private String name;
    /**
    * 驱动名称
    */
    private String driverClassName;
    /**
    * 驱动名称
    */
    private String url;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 状态
    */
    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataSourceNo() {
        return dataSourceNo;
    }

    public void setDataSourceNo(String dataSourceNo) {
        this.dataSourceNo = dataSourceNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}