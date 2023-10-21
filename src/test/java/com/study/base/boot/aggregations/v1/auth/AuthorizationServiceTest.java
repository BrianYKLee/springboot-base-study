package com.study.base.boot.aggregations.v1.auth;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[서비스] Authorization(인가")
@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthorizationServiceTest {
//mock -> 가상 임시데이터


    @InjectMocks //Mock주입 가상의 서비스 주입
    private AuthorizationService authorizationService;

    @BeforeEach
    void init(){
        ReflectionTestUtils.setField(authorizationService, "accessKey", "spring-boot-base-study-auth");
    }

    @Test
    void 토큰_생성() {
        //given-> 데스트용 사전데이터

        //when - 테스트 실행
        final var accessToken = authorizationService.createAccessToken();

        //then - 테스트 결과확인
        assertAll(
                () -> assertNotNull(accessToken)
        );

        log.info("accessToken :: {}", accessToken);
    }

}