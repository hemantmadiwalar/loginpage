package com.example.login.service;

import com.example.login.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserSecurityService {

    private static String secret="How_You_Doing";
    private static long expiration = 10*60*1000;

    public String GenerateJwtToken(UserEntity user) {

        long millisec = System.currentTimeMillis()+expiration;
        Date issuedate = new Date(millisec);

        Claims claims = Jwts.claims().setIssuer(String.valueOf
                (user.getUserId())).setIssuedAt(issuedate).setExpiration(issuedate);

        claims.put("name", user.getUserName());

        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secret).compact();

        return token;
    }


    public boolean VerifyJwtToken(String validate) throws Exception {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(validate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
