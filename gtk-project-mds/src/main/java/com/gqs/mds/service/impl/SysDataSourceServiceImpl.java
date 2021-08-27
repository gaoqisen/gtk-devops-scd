package com.gqs.mds.service.impl;

import com.gqs.mds.config.DbContextHolder;
import com.gqs.mds.config.DynamicDataSource;
import com.gqs.mds.entity.SysDataSource;
import com.gqs.mds.dao.SysDataSourceDao;
import com.gqs.mds.service.SysDataSourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 数据源(SysDataSource)表服务实现类
 *
 * @author makejava
 * @since 2021-08-26 17:14:25
 */
@Service("sysDataSourceService")
public class SysDataSourceServiceImpl implements SysDataSourceService {

    @Resource
    private SysDataSourceDao sysDataSourceDao;

    @Resource
    private DynamicDataSource dynamicDataSource;

    @Resource(name = "taskExecutor")
    private ExecutorService executorService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDataSource queryById(Long id) {
        return this.sysDataSourceDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysDataSource> queryAllByLimit(int offset, int limit) {

        return this.sysDataSourceDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysDataSource 实例对象
     * @return 实例对象
     */
    @Override
    public SysDataSource insert(SysDataSource sysDataSource) {
        // Todo 通过数据源编号判断数据库是否存在

        // 入库后设置数据源
        this.sysDataSourceDao.insert(sysDataSource);
        dynamicDataSource.setDataSource(sysDataSource.getDataSourceNo(), sysDataSource);

        // 测试TTL
        DbContextHolder.setVal("945645456");
        executorService.submit(() -> {
            System.out.println("异步执行！" + DbContextHolder.getVal());
        });
        return sysDataSource;
    }

    /**
     * 修改数据
     *
     * @param sysDataSource 实例对象
     * @return 实例对象
     */
    @Override
    public SysDataSource update(SysDataSource sysDataSource) {
        this.sysDataSourceDao.update(sysDataSource);
        return this.queryById(sysDataSource.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysDataSourceDao.deleteById(id) > 0;
    }
}