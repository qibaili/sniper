package com.qibaili.sniper.web.system.repository;

import com.qibaili.sniper.web.system.entity.SystemResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-06
 */
public interface SystemResourceRepository extends JpaRepository<SystemResource, String>, JpaSpecificationExecutor<SystemResource> {

    @Query(value = "SELECT permission FROM system_resource WHERE id IN (SELECT resource_id FROM system_role_resource " +
            "WHERE role_id IN (SELECT role_id FROM system_user_role WHERE user_id = (SELECT id FROM system_user WHERE username = ?1)))",
            nativeQuery = true)
    List<String> findPermissionByUsername(String username);

    List<SystemResource> findByParentIdIsNullOrderBySort();

    List<SystemResource> findByParentIdOrderBySort(String parentId);

    @Query(value = "SELECT * FROM system_resource WHERE id IN (SELECT resource_id FROM system_role_resource WHERE role_id = ?1)",
            nativeQuery = true)
    List<SystemResource> findByRoleId(String roleId);
}
