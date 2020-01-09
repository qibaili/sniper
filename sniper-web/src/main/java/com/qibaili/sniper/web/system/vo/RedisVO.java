package com.qibaili.sniper.web.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author qibaili
 * @date 2019/11/19
 */
@Data
@AllArgsConstructor
public class RedisVO {

    private String key;

    private String value;
}
