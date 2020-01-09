package com.qibaili.sniper.web.system.controller;

import com.qibaili.sniper.web.system.annotation.SystemLogs;
import com.qibaili.sniper.web.system.bean.ResponseResult;
import com.qibaili.sniper.web.system.dto.UserFindDTO;
import com.qibaili.sniper.web.system.entity.SystemUser;
import com.qibaili.sniper.web.system.service.SystemUserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qibaili
 * @date 2019/11/26
 */
@RestController
@RequestMapping(value = "system/user")
@Api(tags = {"系统：用户管理"})
public class SystemUserController {

    @Autowired
    private SystemUserService userService;

    @GetMapping
    @ApiOperation("查询用户")
    @SystemLogs("查询用户")
    @PreAuthorize("@permission.check('system:user:list')")
    public ResponseResult getList(@Validated UserFindDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() <= 0 ? 0 : dto.getPage() - 1, dto.getPageSize(),
                Sort.by(Sort.Direction.ASC, "createTime"));
        Page<SystemUser> page = userService.findAll(dto, pageable);
        return ResponseResult.e(HttpStatus.OK.value(), "", true, page);
    }
}
