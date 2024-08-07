package com.watermelon.server.randomevent.auth.resolver;

import com.watermelon.server.randomevent.auth.annotations.Uid;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.watermelon.server.common.constants.HttpConstants.HEADER_UID;

@Component
@Slf4j
public class UidArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        //Uid 어노테이션, String type 을 가진 인자에 주입
        return parameter.getParameterType().equals(String.class) && parameter.hasParameterAnnotation(Uid.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        //header 의 uid 를 주입
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getAttribute(HEADER_UID);
    }

}
