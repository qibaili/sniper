package com.qibaili.sniper.web.system.util;

import com.qibaili.sniper.web.system.exception.RequestException;
import com.qibaili.sniper.web.system.security.JwtUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author qibaili
 * @date 2019/11/25
 */
public class SecurityUtils {

    public static JwtUser getUserDetails() {
        JwtUser jwtUser;
        try {
            jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RequestException(HttpStatus.UNAUTHORIZED, "登录状态过期");
        }
        return jwtUser;
    }

    public static String encodePassword(String password) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(password);
    }
}
