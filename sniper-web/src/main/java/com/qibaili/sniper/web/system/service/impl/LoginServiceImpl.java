package com.qibaili.sniper.web.system.service.impl;

import com.qibaili.sniper.web.system.bean.ResponseResult;
import com.qibaili.sniper.web.system.dto.LoginDTO;
import com.qibaili.sniper.web.system.exception.RequestException;
import com.qibaili.sniper.web.system.security.AuthInfo;
import com.qibaili.sniper.web.system.security.JwtUser;
import com.qibaili.sniper.web.system.service.LoginService;
import com.qibaili.sniper.web.system.service.OnlineUserService;
import com.qibaili.sniper.web.system.service.RedisService;
import com.qibaili.sniper.web.system.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author qibaili
 * @date 2018-06-08
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private OnlineUserService onlineUserService;

    @Override
    public ResponseResult login(LoginDTO dto, HttpServletRequest request) {
        // 查询验证码
        String code = redisService.getCodeValue(dto.getCodeKey());
        // 清除验证码
        redisService.delete(dto.getCodeKey());
        if (StringUtils.isEmpty(code)) {
            throw new RequestException("验证码已过期");
        }
        if (StringUtils.isEmpty(dto.getCode()) || !dto.getCode().equalsIgnoreCase(code)) {
            throw new RequestException("验证码错误");
        }
        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(dto.getUsername());

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (!encoder.matches(dto.getPassword(), jwtUser.getPassword())) {
            throw new AccountExpiredException("密码错误");
        }

        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }
        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);
        // 保存在线信息
        onlineUserService.save(jwtUser, token, request);
        // 返回 token
        return ResponseResult.e(OK.value(), "登陆成功", true, new AuthInfo(token, jwtUser));
    }
}
