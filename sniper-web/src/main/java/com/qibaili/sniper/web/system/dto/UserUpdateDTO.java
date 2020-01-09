package com.qibaili.sniper.web.system.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author qibaili
 * @date 2018-05-27
 */
@Data
public class UserUpdateDTO {

    @Email(message = "错误的电子邮箱格式")
    private String email;

    @NotBlank(message = "电话不能为空")
    private String phone;
}
