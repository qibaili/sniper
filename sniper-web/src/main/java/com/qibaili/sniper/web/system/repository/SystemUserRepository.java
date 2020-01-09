package com.qibaili.sniper.web.system.repository;

import com.qibaili.sniper.web.system.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-06
 */
public interface SystemUserRepository extends JpaRepository<SystemUser, String>, JpaSpecificationExecutor<SystemUser> {

    SystemUser findByUsername(String username);

    @Query(value = "SELECT * FROM system_user WHERE id IN (SELECT user_id FROM system_user_role WHERE role_id = ?1) ORDER BY create_time",
            nativeQuery = true)
    List<SystemUser> findByRoleId(String roleId);

}
