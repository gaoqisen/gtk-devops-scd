package com.gtk.bsp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gtk.bsp.entity.SysCode;
import com.gtk.bsp.entity.SysUserEntity;
import com.gtk.bsp.form.SysLoginForm;
import com.gtk.bsp.service.SysCaptchaService;
import com.gtk.bsp.service.SysCodeService;
import com.gtk.bsp.service.SysUserService;
import com.gtk.bsp.service.SysUserTokenService;
import com.gtk.bsp.utils.MD5Utils;
import com.gtk.bsp.utils.RedisUtils;
import com.gtk.bsp.utils.Result;
import com.gtk.bsp.utils.TokenGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "sso")
public class ThirdSSOController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private SysCodeService sysCodeService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response, String redirect, String clientId){
        String sessionId = request.getSession().getId();
        String redisStr = redisUtils.get(sessionId);
        try {
            if (redisStr == null || redisStr.isEmpty()) {
                response.sendRedirect("http://localhost:8000/#/login?clientId=" + clientId + "&redirect="+ redirect);

            } else {
                // 用户登录过，直接生成code重定向到redirect
                response.sendRedirect(redirect);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody SysLoginForm form, HttpServletRequest request, HttpServletResponse response){
        // 校验clientId
        SysCode sysCode = sysCodeService.getOne(new QueryWrapper<SysCode>().eq("client_id", form.getClientId()));
        if(sysCode == null) {
            return Result.error("客服端ID不正确");
        }
        String sessionId = request.getSession().getId();
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if(!captcha){
            return Result.error("验证码不正确");
        }

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return Result.error("账号或密码不正确");
        }

        //账号锁定
        if(user.getStatus() == 0){
            return Result.error("账号已被锁定,请联系管理员");
        }

        //生成code，把code返回给调用方。之后通过code可以获取用户信息
        String code = UUID.randomUUID().toString();
        redisUtils.set(SESSIONID + sessionId, user);
        redisUtils.set(USERCODE + code, user);
        // 重定向到redirect并携带code
        try {
            response.sendRedirect(form.getRedirect() + "?code="+ code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok().put("code" ,code);
    }

    private static final String SESSIONID = "USERSESISON";

    private static final String USERCODE = "USERCODE";
    /**
     * 通过code获取token
     * @param code
     * @return
     */
    @RequestMapping(value = "getUserByCode", method = RequestMethod.POST)
    public Map<String, Object> getUserByCode(String code, String clientId, String sign, String time) {

        if(StringUtils.isEmpty(code)) {
            return Result.error("code获取失败");
        }

        if(StringUtils.isEmpty(clientId)) {
            return Result.error("clientId获取失败");
        }

        SysCode sysCode = sysCodeService.getOne(new QueryWrapper<SysCode>().eq("client_id", clientId));
        if(sysCode == null) {
            return Result.error("客服端ID不正确");
        }

        String currentSign = MD5Utils.getMD5( clientId + sysCode.getSecretKey() + time + code);
        if(currentSign.equals(sign)) {
            return Result.error("SIGN不正确");
        }

        // 时间戳验证
        Long dateTime = System.currentTimeMillis();
        if (Long.valueOf(time) < dateTime - (10*1000) || Long.valueOf(time) > dateTime + (10*1000)) {
            return Result.error("TIME不正确");
        }

        SysUserEntity userEntity = redisUtils.get(USERCODE + code,SysUserEntity.class);

        return Result.ok().putData(userEntity);
    }

}
