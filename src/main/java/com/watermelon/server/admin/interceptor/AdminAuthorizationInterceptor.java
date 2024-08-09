package com.watermelon.server.admin.interceptor;

import com.watermelon.server.admin.service.AdminUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.watermelon.server.common.constants.HttpConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    private final AdminUserService adminUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.debug("preHandle");
        String uid = (String) request.getAttribute(HEADER_UID);
        adminUserService.authorize(uid);

        return true;

    }

}
