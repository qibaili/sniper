package com.qibaili.sniper.web.system.security;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author qibaili
 * @date 2019/11/13
 */
@Data
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 4689315924450011120L;

    @JSONField(serialize=false)
    private String id;

    private String username;

    @JSONField(serialize=false)
    private String password;

    private String avatar;

    private String email;

    private String phone;

    @JSONField(serialize=false)
    private Collection<GrantedAuthority> authorities;

    private String status;

    private boolean enabled;

    private Date createTime;

    private Date lastPasswordRestTime;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
