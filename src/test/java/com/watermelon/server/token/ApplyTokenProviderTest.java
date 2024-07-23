package com.watermelon.server.token;

import com.watermelon.server.ServerApplication;
import com.watermelon.server.error.ApplyTicketWrongException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = ServerApplication.class)
class ApplyTokenProviderTest {

    @Autowired
    ApplyTokenProvider applyTokenProvider;

    @Test
    @DisplayName("JWT APPLY TOKEN 토큰 발급 검증")
    void createApplyToken() throws ApplyTicketWrongException {
        String testQuizId = "testQuizId";
        JwtPayload payload = JwtPayload.builder()
                .quizId(testQuizId)
                .build();
        String accessToken = applyTokenProvider.createTokenByQuizId(payload);
        Assertions.assertThat(accessToken).isNotNull();

        JwtPayload payLoad1 = applyTokenProvider.verifyToken(accessToken);
        Assertions.assertThat(payLoad1.getQuizId()).isEqualTo(testQuizId);

    }
    @Test
    @DisplayName("JWT APPLY TOKEN 토큰 발급 검증")
    void wrongSecretKey(){
        String testQuizId = "testQuizId";
        JwtPayload payload = JwtPayload.builder()
                .quizId(testQuizId)
                .build();
        String accessToken = Jwts.builder()
                .claim("quizId", payload.getQuizId())
                .issuer("test")
                .signWith(Jwts.SIG.HS256.key().build())
                .compact();
        Assertions.assertThatThrownBy(()->applyTokenProvider.verifyToken(accessToken))
                .isInstanceOf(ApplyTicketWrongException.class);

    }



}