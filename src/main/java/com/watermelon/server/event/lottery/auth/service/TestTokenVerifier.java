package com.watermelon.server.event.lottery.auth.service;

import com.watermelon.server.admin.domain.AdminUser;
import com.watermelon.server.admin.repository.AdminUserRepository;
import com.watermelon.server.admin.service.AdminUserService;
import com.watermelon.server.event.lottery.auth.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("local")
@RequiredArgsConstructor
@Slf4j
public class TestTokenVerifier implements TokenVerifier{

    public static String TEST_VALID_TOKEN = "test_valid_token";
    public static String TEST_INVALID_TOKEN = "test_invalid_token";
    public static String TEST_UID = "TEST_UID";
    private final AdminUserRepository adminUserRepository;

    @Override
    public String verify(String token) throws InvalidTokenException {
        if(!token.equals(TEST_VALID_TOKEN)) throw new InvalidTokenException();
        return TEST_UID;
    }

    @PostConstruct
    public void registrationAdmin(){
        log.info("register admin");
        adminUserRepository.save(AdminUser.create(TEST_UID));
    }
}