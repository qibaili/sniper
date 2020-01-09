package com.qibaili.sniper.web.system.controller;


import com.qibaili.sniper.web.system.annotation.SystemLogs;
import com.qibaili.sniper.web.system.bean.ResponseResult;
import com.qibaili.sniper.web.system.dto.LogFindDTO;
import com.qibaili.sniper.web.system.entity.SystemLog;
import com.qibaili.sniper.web.system.service.SystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author qibaili
 * @date 2019/11/4
 */
@RestController
@RequestMapping(value = "system/log")
@Api(tags = {"系统：操作日志"})
public class SystemLogController {

    @Autowired
    private SystemLogService logService;

    @GetMapping
    @PreAuthorize("@permission.check('system:log')")
    @ApiOperation("查询操作日志")
    @SystemLogs(value = "查询操作日志")
    public ResponseResult getList(@Validated LogFindDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() <= 0 ? 0 : dto.getPage() - 1, dto.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));
        Page<SystemLog> page = logService.findAll(dto, pageable);
        return ResponseResult.e(OK.value(), "", true, page);
    }
}
