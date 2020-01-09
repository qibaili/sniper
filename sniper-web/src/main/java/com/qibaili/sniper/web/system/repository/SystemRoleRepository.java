package com.qibaili.sniper.web.system.repository;

import com.qibaili.sniper.web.system.entity.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-06
 */
public interface SystemRoleRepository extends JpaRepository<SystemRole, String>, JpaSpecificationExecutor<SystemRole> {

    SystemRole findByName(String name);

    @Query(value = "SELECT name FROM system_role WHERE id IN (SELECT role_id FROM system_user_role WHERE user_id = (SELECT id FROM system_user WHERE username = ?1))",
            nativeQuery = true)
    List<String> findByUsername(String username);

    @Query(value = "SELECT * FROM system_role WHERE id IN (SELECT role_id FROM system_user_role WHERE user_id = ?1)", nativeQuery = true)
    List<SystemRole> findByUserId(String userId);
}
