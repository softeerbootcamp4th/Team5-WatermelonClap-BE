package com.watermelon.server.admin.service;

import com.watermelon.server.admin.exception.AdminNotAuthorizedException;
import com.watermelon.server.admin.repository.AdminUserRepository;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceTest {

    @Mock
    private AdminUserRepository adminUserRepository;

    @InjectMocks
    private AdminUserService adminUserService;

    @Test
    @DisplayName("uid 를 가진 어드민 유저가 없으면 예외를 던진다.")
    void authorize() {

        //given
        Mockito.when(adminUserRepository.existsByUid(TEST_NOT_UID)).thenReturn(false);

        //when & then
        Assertions.assertThatThrownBy(() -> adminUserService.authorize(TEST_NOT_UID))
                .isInstanceOf(AdminNotAuthorizedException.class);

    }
}