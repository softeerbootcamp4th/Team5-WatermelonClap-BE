package com.watermelon.server.randomevent.auth.interceptor;

import com.watermelon.server.auth.exception.AuthenticationException;
import com.watermelon.server.auth.interceptor.LoginCheckInterceptor;
import com.watermelon.server.auth.service.TokenVerifier;
import com.watermelon.server.randomevent.link.service.LinkService;
import com.watermelon.server.randomevent.service.LotteryService;
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
import static com.watermelon.server.common.constants.HttpConstants.HEADER_LINK_ID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginCheckInterceptorTest {

    @Mock
    private TokenVerifier tokenVerifier;

    @Mock
    private LotteryService lotteryService;

    @Mock
    private LinkService linkService;

    @InjectMocks
    private LoginCheckInterceptor loginCheckInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("tokenVerifier 가 UID 를 정상적으로 반환하면 true 를 반환한다.")
    void preHandleSuccessCase() {

        //given
        mockVerifyForUser();

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
        mockNotVerifyForUser();

        //when & then
        Assertions.assertThatThrownBy(() -> loginCheckInterceptor.preHandle(
                request,
                response, null)).isInstanceOf(AuthenticationException.class);

    }

    @Test
    @DisplayName("처음 로그인할 경우 회원가입한다.")
    void preHandleFirstLoginCaseRegistrationTest(){

        //given
        mockVerifyForUser();
        Mockito.when(lotteryService.isExist(TEST_UID)).thenReturn(false);

        //when
        loginCheckInterceptor.preHandle(request, response, null);

        //then
        verify(lotteryService).registration(TEST_UID);

    }

    @Test
    @DisplayName("처음 로그인하고, 특정 링크로 부터 접속했을 경우 해당 링크의 조회수를 증가시킨다.")
    void preHandleFirstLoginCaseLinkViewTest(){

        //given
        mockVerifyForUser();
        Mockito.when(request.getHeader(HEADER_LINK_ID)).thenReturn(TEST_URI);

        //when
        loginCheckInterceptor.preHandle(request, response, null);

        //then
        verify(linkService).addLinkViewCount(TEST_URI);

    }

    private void mockVerifyForUser(){
        Mockito.when(request.getHeader(HEADER_NAME_AUTHORIZATION)).thenReturn(HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN);
        Mockito.when(tokenVerifier.verify(TEST_TOKEN)).thenReturn(TEST_UID);
    }

    private void mockNotVerifyForUser(){
        Mockito.doThrow(AuthenticationException.class).when(tokenVerifier).verify(TEST_TOKEN);
        Mockito.when(request.getHeader(HEADER_NAME_AUTHORIZATION)).thenReturn(HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN);
    }

}
