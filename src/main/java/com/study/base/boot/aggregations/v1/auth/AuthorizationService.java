package com.study.base.boot.aggregations.v1.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {

    @Value("${jwt.access-token.key}")
    private String accessKey;



    public String createAccessToken() {
        final String issuer = "BASE"; //Accress Token 생성시 보통 서버 명을 넣는다ㅣ
        final String subject = "access";// access 토큰이므로 제목을 엑세스로 준다.
        final String audience = "1";// 발급대상 (로그인 유저의 pK)
        final Date expiredAt = Date.from(Instant.now().plus(Duration.ofDays(1L))); //1일
        final Date notBeforeAt = Date.from(Instant.now());//토큰 발급 시점부터 사용가능
        final Date issuedAt = Date.from(Instant.now()); // 발급시간
        final String jwtId = UUID.randomUUID().toString(); //jwt 식별 id
        final SecretKey signatureKey = Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encodeToString(accessKey.getBytes())
                        .getBytes()
        ); //서명키

        final String accessToken  = Jwts.builder()
                .setIssuer(issuer)        // 발급자(iss)
                .setSubject(subject)       // 제목(sub)
                .setAudience(audience)      //발급대상(aud)
                .setExpiration(expiredAt) // 만료시간( exp)
                .setNotBefore(notBeforeAt) // 토큰 활성날짜(nbf)
                .setIssuedAt(issuedAt) // 발급 시간 (iat)
                .setId(jwtId)     /// jwt 식별자 (jti)
                .signWith(signatureKey)  //서명키
                .compact();

        return accessToken;
    }
    //맥 커맨드 + 시프트 + T누르기

}
