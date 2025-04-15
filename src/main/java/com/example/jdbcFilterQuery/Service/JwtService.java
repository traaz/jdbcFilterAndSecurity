package com.example.jdbcFilterQuery.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private String SECRET_KEY ="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private final long EXPIRATION_TIME = 1000 * 60 * 1; //1 dk

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    public String generateToken(String username){

        return Jwts.builder()

                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    //jwt validation islemleri icin gerekli metodlar
    public String extractUserName(String token){
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token){
        try{
            parseClaims(token);
            return true;
        }catch (JwtException e){

            return false;
        }
    }

    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) //basta olusturulan token key ile olusturmustuk burada da key ile uyumlu mu diye kontrol ettiik
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
