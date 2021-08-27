package com.gqs.mds.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("execution(public * com.gqs.mds.controller.*.*(..))")
    public void switchDataSource(){}

    /**
     * 切面所有的controller，通过header里面的dataSourceNo去进行数据源切换，如果没有获取到则使用默认数据源
     */
    @Before("switchDataSource()")
    public void doBefore(JoinPoint joinPoint) {
        //获取请求报文头部元数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求对象
        assert requestAttributes != null;
        HttpServletRequest request=requestAttributes.getRequest();
        String dataSourceNo = request.getHeader("dataSourceNo");
        if(dataSourceNo == null || dataSourceNo.isEmpty()) {
            DbContextHolder.setVal("default");
        } else {
            DbContextHolder.setVal(dataSourceNo);
        }
    }

}
