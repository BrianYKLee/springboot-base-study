package com.study.base.boot.aggregations.v1.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    @Value("${jwt.access-token.key}")
    private String accessKey;

    public boolean doAuthentication(String accessToken){

        final SecretKey signatureKey = Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encodeToString(accessKey.getBytes())
                        .getBytes()
        );


        try{
            Jwts.parser()
                    .setSigningKey(signatureKey)
                    .build()
                    .parseSignedClaims(accessToken);
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
        return true;
    }

}
