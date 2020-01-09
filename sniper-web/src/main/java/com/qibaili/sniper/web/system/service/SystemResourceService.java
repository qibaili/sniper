package com.qibaili.sniper.web.system.service;

import com.qibaili.sniper.web.system.dto.ResourceAddDTO;
import com.qibaili.sniper.web.system.dto.ResourceUpdateDTO;
import com.qibaili.sniper.web.system.entity.SystemResource;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-07
 */
public interface SystemResourceService {

    // UserPermission getUserPermissionByUsername(String username);

    List<SystemResource> findAll();

    List<SystemResource> findByRoleId(String roleId);

    void save(ResourceAddDTO dto);

    void update(String id, ResourceUpdateDTO dto);

    void delete(String id);
}
