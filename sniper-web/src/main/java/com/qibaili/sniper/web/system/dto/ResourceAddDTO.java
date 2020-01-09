package com.qibaili.sniper.web.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author qibaili
 * @date 2018-08-14
 */
@Data
public class ResourceAddDTO {

    private String parentId;

    @NotBlank(message = "资源名称不能为空")
    private String name;

    @NotBlank(message = "权限编码不能为空")
    private String permission;

    @NotNull(message = "是否必选不能为空")
    private String required;

    @NotNull(message = "类型不能为空")
    private String type;

    @NotNull(message = "排序不能为空")
    private Integer sort;
}