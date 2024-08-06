package com.watermelon.server.common.config;

import com.watermelon.server.admin.interceptor.AdminAuthorizationInterceptor;
import com.watermelon.server.randomevent.auth.interceptor.LoginCheckInterceptor;
import com.watermelon.server.randomevent.auth.resolver.UidArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.watermelon.server.common.constants.PathConstants.*;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UidArgumentResolver uidArgumentResolver;
    private final LoginCheckInterceptor loginCheckInterceptor;
    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //uid 관련 ArgumentResolver 등록
        resolvers.add(uidArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .order(1)
                .addPathPatterns(LOTTERIES_INFO)
                .addPathPatterns(LOTTERIES_RANK)
                .addPathPatterns(PARTS)
                .addPathPatterns(PARTS_EQUIP)
                .addPathPatterns(PARTS_REMAIN)
                .addPathPatterns(MY_LINK);
        registry.addInterceptor(adminAuthorizationInterceptor)
                .order(2)
                .addPathPatterns("/admin/**");
    }

}
