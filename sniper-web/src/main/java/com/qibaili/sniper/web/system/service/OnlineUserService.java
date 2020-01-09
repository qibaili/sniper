package com.qibaili.sniper.web.system.service;

import com.qibaili.sniper.web.system.security.JwtUser;
import com.qibaili.sniper.web.system.security.OnlineUser;
import com.qibaili.sniper.web.system.util.AesUtils;
import com.qibaili.sniper.web.system.util.IpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author qibaili
 * @date 2019/11/20
 */
@Service
@SuppressWarnings({"unchecked", "all"})
public class OnlineUserService {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.online}")
    private String onlineKey;

    private final RedisTemplate redisTemplate;

    public OnlineUserService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(JwtUser jwtUser, String token, HttpServletRequest request) {
        String ip = IpUtils.getIp(request);
        String browser = IpUtils.getBrowser(request);
        String address = IpUtils.getCityInfo(ip);
        OnlineUser onlineUser = null;
        try {
            onlineUser = new OnlineUser();
            onlineUser.setUserName(jwtUser.getUsername());
            onlineUser.setBrowser(browser);
            onlineUser.setAddress(address);
            onlineUser.setIp(ip);
            onlineUser.setKey(AesUtils.encrypt(token));
            onlineUser.setLoginTime(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(onlineKey + token, onlineUser);
        redisTemplate.expire(onlineKey + token, expiration, TimeUnit.MILLISECONDS);
    }

    public void logout(String token) {
        String key = onlineKey + token;
        redisTemplate.delete(key);
    }

    public void kickOut(String key) throws Exception {
        String decode = onlineKey + AesUtils.decrypt(key);
        redisTemplate.delete(decode);
    }
}
