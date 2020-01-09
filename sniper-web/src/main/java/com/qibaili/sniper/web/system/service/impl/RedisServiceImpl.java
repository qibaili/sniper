package com.qibaili.sniper.web.system.service.impl;


import com.qibaili.sniper.web.system.service.RedisService;
import com.qibaili.sniper.web.system.vo.RedisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author qibaili
 * @date 2019/11/19
 */
@Service
@SuppressWarnings({"unchecked", "all"})
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${loginCode.expiration}")
    private Long expiration;

    @Value("${jwt.online}")
    private String onlineKey;

    @Value("${jwt.codeKey}")
    private String codeKey;

    @Override
    public Page<RedisVO> findByKey(String key, Pageable pageable) {
        List<RedisVO> list = findByKey(key);
        return new PageImpl<>(list, pageable, list.size());
    }

    @Override
    public List<RedisVO> findByKey(String key) {
        List<RedisVO> list = new ArrayList<>();
        if (!"*".equals(key)) {
            key = "*" + key + "*";
        }
        Set<String> keys = redisTemplate.keys(key);
        for (String s : keys) {
            // 过滤掉权限的缓存
            if (s.contains("role::loadPermissionByUser") || s.contains("user::loadUserByUsername") || s.contains(onlineKey) || s.contains(codeKey)) {
                continue;
            }
            RedisVO vo = new RedisVO(s, Objects.requireNonNull(redisTemplate.opsForValue().get(s)).toString());
            list.add(vo);
        }
        return list;
    }

    @Override
    public String getCodeValue(String key) {
        try {
            return Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void saveCode(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expiration, TimeUnit.MINUTES);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void deleteAll() {
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys.stream().filter(s -> !s.contains(onlineKey)).filter(s -> !s.contains(codeKey)).collect(Collectors.toList()));
    }
}
