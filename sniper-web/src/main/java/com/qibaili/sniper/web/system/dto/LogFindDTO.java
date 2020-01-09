package com.qibaili.sniper.web.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qibaili
 * @date 2018-08-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogFindDTO extends PageDTO{

    private String username;

    private String action;

}
