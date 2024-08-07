package com.watermelon.server;

import com.watermelon.server.admin.service.AdminUserService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockAdminAuthorizationInterceptorConfig {

    @Bean
    public AdminUserService adminUserService() {
        return Mockito.mock(AdminUserService.class);
    }

}
