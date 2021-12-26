//package com.gqs.mds.controller;
//
//import com.github.gaoqisen.annotation.DataSourceSwitch;
//import com.github.gaoqisen.entity.SysDataSource;
//import com.github.gaoqisen.service.SysDataSourceService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * 数据源(SysDataSource)表控制层
// *
// * @author makejava
// * @since 2021-08-26 18:06:21
// */
//@RestController
//@RequestMapping("sysDataSource")
//public class SysDataSourceController {
//    /**
//     * 服务对象
//     */
//    @Resource
//    private SysDataSourceService sysDataSourceService;
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("selectOne")
//    @DataSourceSwitch(dataSourceNo = "sdf")
//    public SysDataSource selectOne(Long id) {
//        return this.sysDataSourceService.queryById(id);
//    }
//
//    @PostMapping("insert")
//    public void insert(@RequestBody SysDataSource dataSource) {
//        sysDataSourceService.insert(dataSource);
//    }
//
//}