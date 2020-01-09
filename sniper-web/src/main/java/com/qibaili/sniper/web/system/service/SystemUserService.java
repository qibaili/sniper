package com.qibaili.sniper.web.system.service;

import com.qibaili.sniper.web.system.dto.UserAddDTO;
import com.qibaili.sniper.web.system.dto.UserFindDTO;
import com.qibaili.sniper.web.system.dto.UserPasswordDTO;
import com.qibaili.sniper.web.system.dto.UserUpdateDTO;
import com.qibaili.sniper.web.system.entity.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-06
 */
public interface SystemUserService {

    String ENABLE = "1";

    String DISABLE = "0";

    String INITIAL_PASSWORD = "system";

    Page<SystemUser> findAll(UserFindDTO dto, Pageable pageable);

    void save(UserAddDTO dto);

    void update(String id, UserUpdateDTO dto);

    void changeStatus(String id);

    void assignRole(String id, List<String> roleIds);

    void resetPassword(String id);

    void changePassword(String id, UserPasswordDTO dto);

    List<SystemUser> findByRoleId(String roleId);
}
