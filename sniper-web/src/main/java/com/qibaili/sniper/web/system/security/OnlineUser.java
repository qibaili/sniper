package com.qibaili.sniper.web.system.security;

import lombok.Data;

import java.util.Date;

/**
 * @author qibaili
 * @date 2019/11/13
 */
@Data
public class OnlineUser {

    private String userName;

    private String browser;

    private String ip;

    private String address;

    private String key;

    private Date loginTime;
}
