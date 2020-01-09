package com.qibaili.sniper.web.system.repository;

import com.qibaili.sniper.web.system.entity.SystemRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-06
 */
public interface SystemRoleResourceRepository extends JpaRepository<SystemRoleResource, String>, JpaSpecificationExecutor<SystemRoleResource> {

    SystemRoleResource findByRoleIdAndResourceId(String roleId, String resourceId);

    List<SystemRoleResource> findByRoleIdAndResourceIdNotIn(String roleId, List<String> resourceIds);

    List<SystemRoleResource> findByRoleId(String roleId);

    List<SystemRoleResource> findByResourceId(String resourceId);
}
