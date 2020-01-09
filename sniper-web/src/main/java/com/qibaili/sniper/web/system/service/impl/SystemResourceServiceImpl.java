package com.qibaili.sniper.web.system.service.impl;

import com.qibaili.sniper.web.system.dto.ResourceAddDTO;
import com.qibaili.sniper.web.system.dto.ResourceUpdateDTO;
import com.qibaili.sniper.web.system.entity.SystemResource;
import com.qibaili.sniper.web.system.exception.RequestException;
import com.qibaili.sniper.web.system.repository.SystemResourceRepository;
import com.qibaili.sniper.web.system.repository.SystemRoleRepository;
import com.qibaili.sniper.web.system.repository.SystemRoleResourceRepository;
import com.qibaili.sniper.web.system.repository.SystemUserRepository;
import com.qibaili.sniper.web.system.service.SystemResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author qibaili
 * @date 2018-06-07
 */
@Service
public class SystemResourceServiceImpl implements SystemResourceService {

    @Autowired
    private SystemResourceRepository resourceRepository;

    @Autowired
    private SystemUserRepository userRepository;

    @Autowired
    private SystemRoleRepository roleRepository;

    @Autowired
    private SystemRoleResourceRepository roleResourceRepository;

    // @Override
    // public UserPermission getUserPermissionByUsername(String username) {
    //     UserPermission userPermission = new UserPermission();
    //     userPermission.setUser(userRepository.findByUsername(username));
    //     userPermission.setRoles(roleRepository.findByUsername(username));
    //     userPermission.setPermissions(resourceRepository.findPermissionByUsername(username));
    //     return userPermission;
    // }

    @Override
    public List<SystemResource> findAll() {
        List<SystemResource> list = resourceRepository.findByParentIdIsNullOrderBySort();
        if (list != null && list.size() > 0) {
            list.forEach(this::findAllChildren);
        }
        return list;
    }

    @Override
    public List<SystemResource> findByRoleId(String roleId) {
        return resourceRepository.findByRoleId(roleId);
    }

    @Override
    public void save(ResourceAddDTO dto) {
        SystemResource resource = new SystemResource();
        BeanUtils.copyProperties(dto, resource);
        resource.setCreateTime(new Date());
        // resource.setCreatePerson(ShiroUtils.getUser().getUsername());
        resourceRepository.save(resource);
    }

    @Override
    public void update(String id, ResourceUpdateDTO dto) {
        Optional<SystemResource> optional = resourceRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RequestException(String.format("更新失败，不存在 ID 为[%s]的资源", id));
        } else {
            SystemResource resource = optional.get();
            BeanUtils.copyProperties(dto, resource);
            // resource.setUpdatePerson(ShiroUtils.getUser().getUsername());
            resource.setUpdateTime(new Date());
            resourceRepository.save(resource);
        }
    }

    @Override
    public void delete(String id) {
        Optional<SystemResource> optional = resourceRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RequestException(String.format("删除失败，不存在 ID 为[%s]的资源", id));
        } else if (roleResourceRepository.findByResourceId(id).size() > 0) {
            throw new RequestException("删除失败，该资源尚有角色关联！");
        } else {
            resourceRepository.delete(optional.get());
        }
    }

    private void findAllChildren(SystemResource resource) {
        List<SystemResource> list = resourceRepository.findByParentIdOrderBySort(resource.getId());
        resource.setChildren(list);
        if (list != null && list.size() > 0) {
            list.forEach(this::findAllChildren);
        }
    }
}
