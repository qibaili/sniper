package com.qibaili.sniper.web.system.config;

import com.qibaili.sniper.web.system.util.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qibaili
 * @date 2019/11/26
 */
@Service(value = "permission")
public class PermissionConfig {

    public Boolean check(String... permissions) {
        // 获取当前用户的所有权限
        List<String> list = SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 如果是匿名访问的，就放行
        String anonymous = "anonymous";
        if (Arrays.asList(permissions).contains(anonymous)) {
            return true;
        }
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return Arrays.stream(permissions).anyMatch(list::contains);
    }
}
