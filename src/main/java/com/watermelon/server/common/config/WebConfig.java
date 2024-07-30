package com.watermelon.server.common.config;

import com.watermelon.server.randomevent.auth.resolver.UidArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UidArgumentResolver uidArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //uid 관련 ArgumentResolver 등록
        resolvers.add(uidArgumentResolver);
    }

}
