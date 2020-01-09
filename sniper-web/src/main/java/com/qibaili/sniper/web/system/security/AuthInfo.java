package com.qibaili.sniper.web.system.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author qibaili
 * @date 2019/11/19
 */
@Data
@AllArgsConstructor
public class AuthInfo {

    private String token;

    private JwtUser jwtUser;
}
