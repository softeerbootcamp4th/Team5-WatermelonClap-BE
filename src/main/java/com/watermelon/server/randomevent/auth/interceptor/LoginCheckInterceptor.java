package com.watermelon.server.randomevent.auth.interceptor;

import com.watermelon.server.randomevent.auth.exception.AuthenticationException;
import com.watermelon.server.randomevent.auth.utils.AuthUtils;
import com.watermelon.server.randomevent.auth.exception.InvalidTokenException;
import com.watermelon.server.randomevent.auth.service.TokenVerifier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.watermelon.server.common.constants.HttpConstants.HEADER_AUTHORIZATION;
import static com.watermelon.server.common.constants.HttpConstants.HEADER_UID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final TokenVerifier tokenVerifier;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthUtils.parseAuthenticationHeaderValue(
                request.getHeader(HEADER_AUTHORIZATION)
        );
        try {
            String uid = tokenVerifier.verify(token);
            request.setAttribute(HEADER_UID, uid);
            return true;
        }catch (InvalidTokenException e) {
            throw new AuthenticationException("invalid token");
        }
    }
}
