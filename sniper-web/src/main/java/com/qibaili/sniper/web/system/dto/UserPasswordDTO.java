package com.qibaili.sniper.web.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author qibaili
 * @date 2019/10/15
 */
@Data
public class UserPasswordDTO {

    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
