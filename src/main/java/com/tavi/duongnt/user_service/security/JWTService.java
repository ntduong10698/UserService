package com.tavi.duongnt.user_service.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.tavi.duongnt.user_service.security.SecurityConstants.*;


@Service
public class JWTService {

    //Tạo token
    public String generateToken(String username, long expirationTime) {

        return TOKEN_PREFIX + JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    //Giải mã token
    public String decode(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build().verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(new JWTService().decode("ShibaInueyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJleHAiOjE1Nzc3ODc1MDR9.lPrUW8h9IcK6Cr29BlB_4fpv1RnGC0wtVlyHtribAPWZlUYFT2bQ3NoBjMhoh8BaO0l10Q_ZYrOwdqnoV3AxRQ"));
    }
}
