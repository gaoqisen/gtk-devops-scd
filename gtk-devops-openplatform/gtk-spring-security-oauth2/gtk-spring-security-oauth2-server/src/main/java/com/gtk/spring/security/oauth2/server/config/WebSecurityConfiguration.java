package com.gtk.spring.security.oauth2.server.config;

import com.gtk.spring.security.oauth2.server.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 配置默认的加密方式
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private FailureAuthenticationHandler failureHandler;

    @Autowired
    private SuccessAuthenticationHandler successHandler;

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 通过数据库创建
        auth.userDetailsService(userDetailsService());

//        // 在内存中创建用户
//        auth.inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder().encode("123456")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("admin888")).roles("ADMIN");
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 将 check_token 暴露出去，否则资源服务器访问时报 403 错误
        web.ignoring().antMatchers("/oauth/check_token");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自定义login.html登录界面
        http.formLogin().loginPage("/login")
                // .loginProcessingUrl("/login) //form 表单action
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                //.usernameParameter("uname")
                //.passwordParameter("psd")
                .and()
                .authorizeRequests()
                // 一定要把自定义登录界面给放开权限。即.antMatchers("/login").permitAll(),否则就是死循环。
                .antMatchers("/login").permitAll()
                // 除了login.html页面以外都需要身份认证 （一定要记得添加这一句，否则就是死循环）
                .anyRequest()
                .authenticated()
        ;
    }
}