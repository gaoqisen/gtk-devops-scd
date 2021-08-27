package com.gqs.mds.dao;

import com.gqs.mds.entity.SysDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据源(SysDataSource)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-26 17:14:24
 */
@Mapper
public interface SysDataSourceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDataSource queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDataSource> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysDataSource 实例对象
     * @return 对象列表
     */
    List<SysDataSource> queryAll(SysDataSource sysDataSource);

    /**
     * 新增数据
     *
     * @param sysDataSource 实例对象
     * @return 影响行数
     */
    int insert(SysDataSource sysDataSource);

    /**
     * 修改数据
     *
     * @param sysDataSource 实例对象
     * @return 影响行数
     */
    int update(SysDataSource sysDataSource);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}