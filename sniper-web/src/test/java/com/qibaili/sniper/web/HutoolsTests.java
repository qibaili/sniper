package com.qibaili.sniper.web;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author qibaili
 * @date 2019/12/3
 */
@SpringBootTest
public class HutoolsTests {

    @Test
    public void idTest() {
        Snowflake snowflake = IdUtil.createSnowflake(0, 0);
        for (int i = 0; i < 10; i++) {
            System.out.println(snowflake.nextId());
        }

        System.out.println("------------------------");

         snowflake = IdUtil.createSnowflake(0, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(snowflake.nextId());
        }
    }

}
