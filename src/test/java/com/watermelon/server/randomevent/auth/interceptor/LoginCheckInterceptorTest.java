package com.watermelon.server.randomevent.auth.interceptor;

import com.watermelon.server.randomevent.auth.exception.AuthenticationException;
import com.watermelon.server.randomevent.auth.service.TokenVerifier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.watermelon.server.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class LoginCheckInterceptorTest {

    @Mock
    private TokenVerifier tokenVerifier;

    @InjectMocks
    private LoginCheckInterceptor loginCheckInterceptor;

    @Test
    @DisplayName("tokenVerifier 가 UID 를 정상적으로 반환하면 true 를 반환한다.")
    void preHandleSuccessCase() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Mockito.when(tokenVerifier.verify(TEST_TOKEN)).thenReturn(TEST_UID);
        Mockito.when(request.getHeader(HEADER_NAME_AUTHORIZATION)).thenReturn(HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN);

        //when
        boolean actual = loginCheckInterceptor.preHandle(
                request,
                response, null);

        //then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("tokenVerifier 가 예외를 발생시키면 Authentication Exception 을 발생시킨다.")
    void preHandleFailureCase() {

        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Mockito.doThrow(AuthenticationException.class).when(tokenVerifier).verify(TEST_TOKEN);
        Mockito.when(request.getHeader(HEADER_NAME_AUTHORIZATION)).thenReturn(HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN);

        //when & then
        Assertions.assertThatThrownBy(() -> loginCheckInterceptor.preHandle(
                request,
                response, null)).isInstanceOf(AuthenticationException.class);

    }

}