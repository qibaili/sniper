package com.qibaili.sniper.web.system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author qibaili
 * @date 2018/11/1
 */
@Data
public class UserAddDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能小于6位字符！")
    private String password;

    @Email(message = "错误的电子邮箱格式")
    private String email;

    @NotBlank(message = "电话不能为空")
    private String telephone;
}
