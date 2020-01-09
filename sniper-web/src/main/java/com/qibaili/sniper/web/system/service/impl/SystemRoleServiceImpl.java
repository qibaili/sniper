package com.qibaili.sniper.web.system.service.impl;


import com.qibaili.sniper.web.system.dto.RoleAddDTO;
import com.qibaili.sniper.web.system.dto.RoleUpdateDTO;
import com.qibaili.sniper.web.system.entity.SystemRole;
import com.qibaili.sniper.web.system.entity.SystemRoleResource;
import com.qibaili.sniper.web.system.exception.RequestException;
import com.qibaili.sniper.web.system.repository.SystemResourceRepository;
import com.qibaili.sniper.web.system.repository.SystemRoleRepository;
import com.qibaili.sniper.web.system.repository.SystemRoleResourceRepository;
import com.qibaili.sniper.web.system.service.SystemRoleService;
import com.qibaili.sniper.web.system.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author qibaili
 * @date 2018-06-06
 */
@Service
@Slf4j
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleRepository roleRepository;

    @Autowired
    private SystemRoleResourceRepository roleResourceRepository;

    @Autowired
    private SystemResourceRepository resourceRepository;

    @Override
    public Page<SystemRole> findAll(String name, Pageable pageable) {
        Specification<SystemRole> specification = createSpecification(name);
        return roleRepository.findAll(specification, pageable);
    }

    @Override
    public void save(RoleAddDTO dto) {
        try {
            if (roleRepository.findByName(dto.getName()) != null) {
                throw new RequestException("角色名已存在，请重新输入");
            }
            SystemRole role = new SystemRole();
            role.setName(dto.getName());
            role.setCreatePerson(SecurityUtils.getUserDetails().getUsername());
            role.setCreateTime(new Date());
            roleRepository.save(role);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(String id, RoleUpdateDTO dto) {
        Optional<SystemRole> optional = roleRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RequestException(String.format("角色更新失败，不存在 ID 为 [%s] 的角色", id));
        }
        SystemRole sysRole = roleRepository.findByName(dto.getName());
        if (sysRole != null && !id.equals(sysRole.getId())) {
            throw new RequestException("角色更新失败，角色名称已存在，请重新输入");
        }
        SystemRole role = optional.get();
        role.setName(dto.getName());
        role.setUpdatePerson(SecurityUtils.getUserDetails().getUsername());
        role.setUpdateTime(new Date());
        roleRepository.save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignPermissions(String id, List<String> resourceIds) {
        if (!roleRepository.findById(id).isPresent()) {
            throw new RequestException(String.format("角色关联权限失败，不存在 ID 为 [%s] 的角色", id));
        }
        if (resourceIds != null && resourceIds.size() > 0) {

            // 给角色分配新资源
            for (String resourceId : resourceIds) {
                if (!resourceRepository.findById(resourceId).isPresent()) {
                    throw new RequestException(String.format("角色关联权限失败，不存在 ID 为 [%s] 的资源", resourceId));
                }
                if (roleResourceRepository.findByRoleIdAndResourceId(id, resourceId) != null) {
                    continue;
                }
                SystemRoleResource roleResource = new SystemRoleResource();
                roleResource.setRoleId(id);
                roleResource.setResourceId(resourceId);
                roleResource.setCreateTime(new Date());
                roleResource.setCreatePerson(SecurityUtils.getUserDetails().getUsername());
                roleResourceRepository.save(roleResource);
            }
            // 删除旧【角色-资源】的关联
            List<SystemRoleResource> list = roleResourceRepository.findByRoleIdAndResourceIdNotIn(id, resourceIds);
            if (list != null && list.size() > 0) {
                roleResourceRepository.deleteAll(list);
            }
        } else {
            // 清除角色权限
            List<SystemRoleResource> list = roleResourceRepository.findByRoleId(id);
            if (list != null && list.size() > 0) {
                roleResourceRepository.deleteAll(list);
            }
        }
    }

    @Override
    public List<SystemRole> findByUserId(String userId) {
        return roleRepository.findByUserId(userId);
    }

    private Specification<SystemRole> createSpecification(String name) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (!StringUtils.isEmpty(name)) {
                predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return predicate;
        };
    }
}
