package com.qibaili.sniper.web.system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author qibaili
 * @date 2018/11/12
 */
@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能小于6位字符！")
    private String password;

    private String code;

    private String codeKey;
}
