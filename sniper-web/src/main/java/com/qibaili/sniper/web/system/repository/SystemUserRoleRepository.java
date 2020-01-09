package com.qibaili.sniper.web.system.repository;

import com.qibaili.sniper.web.system.entity.SystemUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-08-15
 */
public interface SystemUserRoleRepository extends JpaRepository<SystemUserRole, String>, JpaSpecificationExecutor<SystemUserRole> {

    List<SystemUserRole> findByUserId(String userId);

    SystemUserRole findByUserIdAndRoleId(String userId, String roleId);

    List<SystemUserRole> findByUserIdAndRoleIdNotIn(String userId, List<String> roleIds);
}
