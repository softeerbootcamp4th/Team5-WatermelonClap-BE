package com.watermelon.server;

import com.watermelon.server.auth.service.TokenVerifier;
import com.watermelon.server.randomevent.link.service.LinkService;
import com.watermelon.server.randomevent.service.LotteryService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.watermelon.server.Constants.TEST_TOKEN;
import static com.watermelon.server.Constants.TEST_UID;

@TestConfiguration
public class MockLoginInterceptorConfig {

    @Bean
    public TokenVerifier mockTokenVerifier() {
        TokenVerifier tokenVerifier = Mockito.mock(TokenVerifier.class);
        Mockito.when(tokenVerifier.verify(TEST_TOKEN)).thenReturn(TEST_UID);
        return tokenVerifier;
    }

    @Bean
    public LinkService mockLinkService() {
        return Mockito.mock(LinkService.class);
    }

    @Bean
    public LotteryService mockLotteryService() {
        return Mockito.mock(LotteryService.class);
    }

}
