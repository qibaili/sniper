package com.qibaili.sniper.web.system.controller;

import cn.hutool.core.util.IdUtil;
import com.qibaili.sniper.web.system.annotation.AnonymousAccess;
import com.qibaili.sniper.web.system.annotation.SystemLogs;
import com.qibaili.sniper.web.system.bean.ResponseResult;
import com.qibaili.sniper.web.system.dto.LoginDTO;
import com.qibaili.sniper.web.system.security.ImageResult;
import com.qibaili.sniper.web.system.service.LoginService;
import com.qibaili.sniper.web.system.service.OnlineUserService;
import com.qibaili.sniper.web.system.service.RedisService;
import com.qibaili.sniper.web.system.util.JwtTokenUtils;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author qibaili
 * @date 2019/11/19
 */
@RestController
@RequestMapping(value = "auth")
@Api(tags = "系统：登陆授权")
public class AuthenticationController {

    @Value("${jwt.codeKey}")
    private String codeKey;

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OnlineUserService onlineUserService;

    @Autowired
    private LoginService loginService;

    @ApiOperation("获取验证码")
    @GetMapping(value = "code")
    @AnonymousAccess
    public ImageResult getCode() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        String result = captcha.text();
        System.out.println(result);
        String key = codeKey + IdUtil.simpleUUID();
        System.out.println(key);
        redisService.saveCode(key, result);
        return new ImageResult(captcha.toBase64(), key);
    }

    @ApiOperation("用户登录")
    @AnonymousAccess
    @PostMapping(value = "login")
    @SystemLogs(value = "用户登陆")
    public ResponseResult login(@Validated LoginDTO dto, HttpServletRequest request) {
        return loginService.login(dto, request);
    }

    @ApiOperation("退出登录")
    @AnonymousAccess
    @DeleteMapping(value = "logout")
    @SystemLogs(value = "退出登录")
    public ResponseResult logout(HttpServletRequest request) {
        onlineUserService.logout(jwtTokenUtil.getToken(request));
        return ResponseResult.e(OK.value(), "", true);
    }
}
