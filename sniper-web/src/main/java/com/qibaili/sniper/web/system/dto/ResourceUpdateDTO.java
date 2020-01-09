package com.qibaili.sniper.web.system.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author qibaili
 * @date 2019/9/26
 */
@Data
public class ResourceUpdateDTO {

    @NotNull(message = "是否必选不能为空")
    private String required;

    @NotNull(message = "类型不能为空")
    private String type;

    @NotNull(message = "排序不能为空")
    private Integer sort;
}
