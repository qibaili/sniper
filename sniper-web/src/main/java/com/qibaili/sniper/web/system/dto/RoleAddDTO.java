package com.qibaili.sniper.web.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author qibaili
 * @date 2018-06-10
 */
@Data
public class RoleAddDTO {

    @NotBlank(message = "角色名称不能为空")
    private String name;

}
