package com.ju.mall.Common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: mall
 * @package: com.ju.mall.Common.utils
 * @className: JwtTokenUtils
 * @author: Eric
 * @description: TODO jwt工具类
 * @date: 2023/5/29 11:14
 * @version: 1.0
 */
@Component
public class JwtTokenUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    /**
     * token 中的声明：用户名,需要和存放时的一致
     */
    private static final String CLAIM_KEY_USERNAME = "sub";
    /**
     * token 中的声明：创建时间
     */
    private static final String CLAIM_KEY_DATE = "createDate";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * @param userDetails: 用户信息
     * @return String
     * @author jcm
     * @description TODO 根据用户信息生成token
     * @date 2023/5/29 11:16
     */
    public String generatorToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_DATE, new Date());
        return this.generatorToken(claims);
    }

    private String generatorToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generatorExpirationDate())
                //加密算法和密钥
                .signWith(SignatureAlgorithm.HS512, secret)
                //压缩为字符串形式
                .compact();
    }

    /**
     * 设置token的过期时间
     *
     * @return
     */
    private Date generatorExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * @param token:
     * @return Claims
     * @author jcm
     * @description TODO 从token中提取声明
     * @date 2023/5/29 11:19
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try{
            //JWT解密
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            LOGGER.error("JWT格式验证失败:{}",token);
        }
        return claims;
    }

    /**
     * @param token:
     * @return String
     * @author jcm
     * @description TODO 从token中获取用户名
     * @date 2023/5/29 11:21
     */
    public String getUserNameFromToken(String token) {
        String username;
        try{
            Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    /**
     * TODO 验证token是否还有效
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = this.getUserNameFromToken(token);
        if(StringUtils.isEmpty(username)){
            return false;
        }
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    /**
     * 到期时间和当前时间比较，如果为true,则说明比当前时间小,到期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token){
        Date expiredDate = this.getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    private Date getExpiredDateFromToken(String token){
        Claims claims = this.getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断是否能刷新token
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !this.isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     */
    public String refreshToken(String token){
        if(!canRefresh(token)){
            LOGGER.info("token已过失效时间,无法刷新:{}",token);
        }
        Claims claims = this.getClaimsFromToken(token);
        claims.put(CLAIM_KEY_DATE,new Date());
        return this.generatorToken(claims);
    }
}
