package com.qibaili.sniper.web;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qibaili
 * @date 2019/11/20
 */
@SpringBootTest
class JwtTests {

    @Test
    void jwtTests1() {
        Map<String, Object> claims = new HashMap<>();
        String subject = "admin";
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + 21600000);
        String secret = "mySecret";
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        System.out.println(token);
    }
}
