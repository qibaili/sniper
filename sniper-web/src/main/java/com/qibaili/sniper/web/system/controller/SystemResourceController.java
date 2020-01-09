package com.qibaili.sniper.web.system.controller;

import com.qibaili.sniper.web.system.annotation.SystemLogs;
import com.qibaili.sniper.web.system.bean.ResponseResult;
import com.qibaili.sniper.web.system.dto.ResourceAddDTO;
import com.qibaili.sniper.web.system.dto.ResourceUpdateDTO;
import com.qibaili.sniper.web.system.entity.SystemResource;
import com.qibaili.sniper.web.system.service.SystemResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-10
 */
@RestController
@RequestMapping("system/resource")
@Api(tags = {"系统：资源管理"})
public class SystemResourceController {

    @Autowired
    private SystemResourceService resourceService;

    @GetMapping
    @ApiOperation("查询资源")
    @SystemLogs("查询资源")
    @PreAuthorize("@permission.check('system:resource:list')")
    public ResponseResult getList() {
        List<SystemResource> list = resourceService.findAll();
        return ResponseResult.e(HttpStatus.OK.value(), "", true, list);
    }

    @GetMapping("{roleId}")
    @ApiOperation("根据 RoleId 查询资源")
    @SystemLogs("根据 RoleId 查询资源")
    @PreAuthorize("@permission.check('system:resource:list')")
    public ResponseResult getListByRoleId(@PathVariable String roleId) {
        List<SystemResource> list = resourceService.findByRoleId(roleId);
        return ResponseResult.e(HttpStatus.OK.value(), "", true, list);
    }

    @PostMapping
    @ApiOperation("新建资源")
    @SystemLogs("新建资源")
    @PreAuthorize("@permission.check('system:resource:add')")
    public ResponseResult createResource(@Validated ResourceAddDTO dto) {
        resourceService.save(dto);
        return ResponseResult.e(HttpStatus.OK.value(), "", true);
    }

    @PutMapping("{id}")
    @ApiOperation("更新资源")
    @SystemLogs("更新资源")
    @PreAuthorize("@permission.check('system:resource:update')")
    public ResponseResult updateResource(@PathVariable String id, @Validated ResourceUpdateDTO dto) {
        resourceService.update(id, dto);
        return ResponseResult.e(HttpStatus.OK.value(), "", true);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除资源")
    @SystemLogs("删除资源")
    @PreAuthorize("@permission.check('system:resource:delete')")
    public ResponseResult deleteResource(@PathVariable String id) {
        resourceService.delete(id);
        return ResponseResult.e(HttpStatus.OK.value(), "", true);
    }

}
