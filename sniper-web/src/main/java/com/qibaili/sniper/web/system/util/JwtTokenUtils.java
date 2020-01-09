package com.qibaili.sniper.web.system.util;

import com.qibaili.sniper.web.system.security.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author qibaili
 * @date 2019/11/13
 */
@Component
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 5446919287801654520L;

    private Clock clock = DefaultClock.INSTANCE;

    /**
     * 生成 jwt 签名的时候使用的秘钥 secret,一般可以从本地配置文件中读取.
     * 它就是你服务端的私钥，在任何场景都不应该流露出去。
     * 一旦客户端得知这个 secret, 那就意味着客户端是可以自我签发 jwt 了。
     */
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.header}")
    private String tokenHeader;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        // 创建 payload 的私有声明,根据特定的业务需要添加，如果要拿这个做验证，一般需要和 jwt 的接收方提前沟通好验证方式
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给 builder 的 claim 赋值，
                // 一旦写在标准的声明赋值之后，就会覆盖了那些标准的声明
                .setClaims(claims)
                .setSubject(subject)
                // jwt 的签发时间
                .setIssuedAt(createdDate)
                // 设置过期时间
                .setExpiration(expirationDate)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getToken(HttpServletRequest request) {
        String requestHeader = request.getHeader(tokenHeader);
        if (requestHeader != null && requestHeader.startsWith(SniperConstant.BEARER)) {
            return requestHeader.substring(7);
        }
        return null;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final Date created = getIssuedAtDateFromToken(token);
        // final Date expiration = getExpirationDateFromToken(token);
        // 如果 token 存在，且 token 创建日期 > 最后修改密码的日期则代表 token 有效
        return (!isTokenExpired(token) && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordRestTime()));
    }

    private Date calculateExpirationDate(Date date) {
        return new Date(date.getTime() + expiration);
    }

}
