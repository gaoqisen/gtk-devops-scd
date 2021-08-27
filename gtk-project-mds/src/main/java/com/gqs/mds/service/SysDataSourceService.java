package com.gqs.mds.service;

import com.gqs.mds.entity.SysDataSource;
import java.util.List;

/**
 * 数据源(SysDataSource)表服务接口
 *
 * @author makejava
 * @since 2021-08-26 17:14:25
 */
public interface SysDataSourceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDataSource queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDataSource> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysDataSource 实例对象
     * @return 实例对象
     */
    SysDataSource insert(SysDataSource sysDataSource);

    /**
     * 修改数据
     *
     * @param sysDataSource 实例对象
     * @return 实例对象
     */
    SysDataSource update(SysDataSource sysDataSource);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}