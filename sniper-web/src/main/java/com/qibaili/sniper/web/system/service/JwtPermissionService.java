package com.qibaili.sniper.web.system.service;

import com.qibaili.sniper.web.system.repository.SystemResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qibaili
 * @date 2019/11/13
 */
@Service
@CacheConfig(cacheNames = "permission")
public class JwtPermissionService {

    @Autowired
    private SystemResourceRepository resourceRepository;

    /**
     * key 的名称如有修改，请同步修改 UserServiceImpl 中的 update 方法
     */
    @Cacheable(key = "'loadPermissionByUser:' + #p0")
    public Collection<GrantedAuthority> authorities(String username) {
        System.out.println("--------------------loadPermissionByUser:" + username + "---------------------");
        List<String> permissions = resourceRepository.findPermissionByUsername(username);
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
