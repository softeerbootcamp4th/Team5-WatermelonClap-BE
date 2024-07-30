package com.watermelon.server.token;

import com.watermelon.server.ServerApplication;
import com.watermelon.server.error.ApplyTicketWrongException;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = ServerApplication.class)
class ApplyTokenProviderTest {

    @Autowired
    ApplyTokenProvider applyTokenProvider;

    @Test
    @DisplayName("정상적으로 JWT APPLY TOKEN 토큰 발급 검증")
    void createApplyToken() throws ApplyTicketWrongException {
        String testEventId = "testEventId";
        JwtPayload payload = JwtPayload.builder()
                .eventId(testEventId)
                .build();
        String accessToken = applyTokenProvider.createTokenByOrderEventId(payload);
        Assertions.assertThat(accessToken).isNotNull();

        JwtPayload payLoad1 = applyTokenProvider.verifyToken(accessToken,testEventId);
        Assertions.assertThat(payLoad1.getEventId()).isEqualTo(testEventId);

    }
    @Test
    @DisplayName("Wrong secret key로 발급한 JWT APPLY TOKEN 토큰 발급 검증")
    void wrongSecretKey(){
        String testEventId = "testQuizId";
        JwtPayload payload = JwtPayload.builder()
                .eventId(testEventId)
                .build();
        String accessToken = Jwts.builder()
                .claim("eventId", payload.getEventId())
                .issuer("test")
                .signWith(Jwts.SIG.HS256.key().build())
                .compact();
        Assertions.assertThatThrownBy(()->applyTokenProvider.verifyToken(accessToken,testEventId))
                .isInstanceOf(ApplyTicketWrongException.class);

    }



}