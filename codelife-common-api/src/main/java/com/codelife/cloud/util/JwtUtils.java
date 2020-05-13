package com.codelife.cloud.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

@Data
@ConfigurationProperties("jwt.config")
public class JwtUtils {

    private String key;

    private Long ttl;

    public String createJwt(String id, String name, Map<String, Object> map) {
        long now = System.currentTimeMillis();
        //失效时间
        long exp = now + ttl;

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setSubject(name)
                //创建时间
                .setIssuedAt(new Date())
                //设置加密
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(new Date(exp));
                if(map != null){
                    for(Map.Entry<String, Object> entry : map.entrySet()){
                        jwtBuilder.claim(entry.getKey(),entry.getValue());
                    }
                }
        String token = jwtBuilder.compact();
        return token;
    }

    public Claims parseJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
