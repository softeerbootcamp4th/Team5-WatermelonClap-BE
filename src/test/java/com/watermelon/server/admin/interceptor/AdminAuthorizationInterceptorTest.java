package com.watermelon.server.admin.interceptor;

import com.watermelon.server.admin.exception.AdminNotAuthorizedException;
import com.watermelon.server.admin.service.AdminUserService;
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

import static com.watermelon.server.Constants.TEST_NOT_UID;
import static com.watermelon.server.Constants.TEST_UID;
import static com.watermelon.server.common.constants.HttpConstants.HEADER_UID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminAuthorizationInterceptorTest {

    @Mock
    private AdminUserService adminUserService;

    @InjectMocks
    private AdminAuthorizationInterceptor adminAuthorizationInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("어드민에 등록된 UID 가 맞으면 true 를 반환한다.")
    void preHandleSuccessCase() {

        //given
        Mockito.when(request.getAttribute(HEADER_UID)).thenReturn(TEST_UID);

        //when
        boolean actual = adminAuthorizationInterceptor.preHandle(request, response, null);

        //then
        assertTrue(actual);

    }

    @Test
    @DisplayName("어드민에 등록된 UID 가 틀리면 예외를 터뜨린다.")
    void preHandleFailureCase() {

        //given
        Mockito.when(request.getAttribute(HEADER_UID)).thenReturn(TEST_NOT_UID);
        Mockito.doThrow(AdminNotAuthorizedException.class).when(adminUserService).authorize(TEST_NOT_UID);

        //when & then
        Assertions.assertThatThrownBy(() -> adminAuthorizationInterceptor.preHandle(request, response, null))
                .isInstanceOf(AdminNotAuthorizedException.class);


    }
}