package com.qibaili.sniper.web.system.controller;

import com.qibaili.sniper.web.system.annotation.SystemLogs;
import com.qibaili.sniper.web.system.bean.ResponseResult;
import com.qibaili.sniper.web.system.dto.PageDTO;
import com.qibaili.sniper.web.system.dto.RoleAddDTO;
import com.qibaili.sniper.web.system.dto.RoleUpdateDTO;
import com.qibaili.sniper.web.system.entity.SystemRole;
import com.qibaili.sniper.web.system.service.SystemRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-09
 */
@RestController
@RequestMapping("system/role")
@Api(tags = {"系统：角色管理"})
public class SystemRoleController {

    @Autowired
    private SystemRoleService roleService;

    @GetMapping
    @ApiOperation(value = "查询角色")
    @SystemLogs("查询角色")
    @PreAuthorize("@permission.check('system:role:list')")
    public ResponseResult getList(String name, PageDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() <= 0 ? 0 : dto.getPage() - 1, dto.getPageSize(),
                Sort.by(Sort.Direction.ASC, "createTime"));
        Page<SystemRole> page = roleService.findAll(name, pageable);
        return ResponseResult.e(HttpStatus.OK.value(), "", true, page);
    }

    @GetMapping("{userId}")
    @ApiOperation(value = "根据 Userid 查询角色")
    @PreAuthorize("@permission.check('system:role:list')")
    public ResponseResult getListByUserid(@PathVariable String userId) {
        List<SystemRole> list = roleService.findByUserId(userId);
        return ResponseResult.e(HttpStatus.OK.value(), "", true, list);
    }

    @PostMapping
    @ApiOperation("新建角色")
    @SystemLogs("新建角色")
    @PreAuthorize("@permission.check('system:role:add')")
    public ResponseResult createRole(@Validated RoleAddDTO dto) {
        roleService.save(dto);
        return ResponseResult.e(HttpStatus.OK.value(), "", true);
    }

    @PutMapping("{id}")
    @ApiOperation("更新角色")
    @SystemLogs("更新角色")
    @PreAuthorize("@permission.check('system:role:update')")
    public ResponseResult updateRole(@PathVariable String id, @Validated RoleUpdateDTO dto) {
        roleService.update(id, dto);
        return ResponseResult.e(HttpStatus.OK.value(), "", true);
    }

    @PutMapping("resource/{id}")
    @ApiOperation("角色分配权限")
    @SystemLogs("角色分配权限")
    @PreAuthorize("@permission.check('system:role:update')")
    public ResponseResult assignPermissions(@PathVariable String id,
                                            @RequestParam(value = "resourceIds", required = false) List<String> resourceIds) {
        roleService.assignPermissions(id, resourceIds);
        return ResponseResult.e(HttpStatus.OK.value(), "", true);
    }
}