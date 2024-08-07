package com.watermelon.server.lottery.auth.interceptor;

import com.watermelon.server.lottery.auth.exception.AuthenticationException;
import com.watermelon.server.lottery.auth.utils.AuthUtils;
import com.watermelon.server.lottery.auth.exception.InvalidTokenException;
import com.watermelon.server.lottery.auth.service.TokenVerifier;
import com.watermelon.server.lottery.link.service.LinkService;
import com.watermelon.server.lottery.service.LotteryService;
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
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final TokenVerifier tokenVerifier;
    private final LotteryService lotteryService;
    private final LinkService linkService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthUtils.parseAuthenticationHeaderValue(
                request.getHeader(HEADER_AUTHORIZATION)
        );
        try {
            String uid = tokenVerifier.verify(token);
            request.setAttribute(HEADER_UID, uid);

            checkFirstLogin(uid, request.getHeader(HEADER_LINK_ID));

            return true;
        }catch (InvalidTokenException e) {
            throw new AuthenticationException("invalid token");
        }
    }

    private void checkFirstLogin(String uid, String linkId){
        if(lotteryService.isExist(uid)) return;

        //만약 등록되지 않은 유저라면
        lotteryService.registration(uid);

        if(linkId==null || linkId.isEmpty()) return;

        //링크 아이디가 존재한다면
        linkService.addLinkViewCount(linkId);

    }

}
