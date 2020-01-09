package com.qibaili.sniper.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author qibaili
 * @date 2019/11/20
 */
@SpringBootTest
class PasswordEncoderTests {

    /**
     * Spring Security 提供了 BCryptPasswordEncoder 类，实现 Spring 的 PasswordEncoder 接口，使用 BCrypt 强哈希方法来加密密码。
     * BCrypt 强哈希方法每次加密相同字符串的结果都不一样，如果需要判断是否是原来的密码，需要用它自带的方法
     */
    @Test
    void testPasswordEncoder1() {
        String password = "dfyc4kiptv+";
        // BCryptPasswordEncoder 方法采用 SHA-256 + 随机盐 + 密钥对密码进行加密，每次生成的 hash 值都是不同的，可以免除存储 salt，暴力破解起来也更困难。
        // SHA 系列是 Hash 算法，不是加密算法，使用加密算法意味着可以解密（这个与编码/解码一样），但是采用 Hash 处理，其过程是不可逆的。
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 加密(encode)
        String code = encoder.encode(password);
        // $2a$10$N6ivj.pPsEGKmA6/LcFzMuVx2.LF15fdEnqUT82NVyGAGDtdFc2Y2
        System.out.println(code);
        // 密码匹配(matches)
        System.out.println(encoder.matches(password, code) ? "相等" : "不相等");
    }

    @Test
    void testPasswordEncoder2() {
        String password = "dfyc4kiptv+";
        // Spring Security 5 默认支持的密码加密方式在 PasswordEncoderFactories 中定义，默认为 bcrypt
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String code = encoder.encode(password);
        // 前缀表示加密方式，这样的好处是同一个系统可以使用多种加密方式，迁移用户到新系统时比较就省事了。
        // {bcrypt}$2a$10$VuqzwDbmEF9OWjABMJUAi.dicA3dgK8Jf8YK.wsWKRv1xiwAM/Dyu
        System.out.println(code);
        System.out.println(encoder.matches(password, code) ? "相等" : "不相等");
    }
}
