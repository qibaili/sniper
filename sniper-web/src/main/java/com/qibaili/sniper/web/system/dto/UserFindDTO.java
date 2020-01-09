package com.qibaili.sniper.web.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qibaili
 * @date 2018-05-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserFindDTO extends PageDTO {

    private String username;

    private String name;
}
