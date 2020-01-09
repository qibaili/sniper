package com.qibaili.sniper.web.system.service.impl;

import com.qibaili.sniper.web.system.entity.SystemUser;
import com.qibaili.sniper.web.system.exception.RequestException;
import com.qibaili.sniper.web.system.repository.SystemUserRepository;
import com.qibaili.sniper.web.system.security.JwtUser;
import com.qibaili.sniper.web.system.service.JwtPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author qibaili
 * @date 2019/11/13
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemUserRepository userRepository;

    @Autowired
    private JwtPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RequestException("账号不存在");
        } else {
            return createJwtUser(user);
        }
    }

    private UserDetails createJwtUser(SystemUser user) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(user.getId());
        jwtUser.setUsername(user.getUsername());
        jwtUser.setPassword(user.getPassword());
        jwtUser.setEmail(user.getEmail());
        jwtUser.setPhone(user.getPhone());
        jwtUser.setAuthorities(permissionService.authorities(user.getUsername()));
        jwtUser.setStatus(user.getStatus());
        jwtUser.setEnabled(!"0".equals(user.getStatus()));
        jwtUser.setCreateTime(user.getCreateTime());
        jwtUser.setLastPasswordRestTime(user.getLastPasswordRestTime());
        return jwtUser;
    }
}
