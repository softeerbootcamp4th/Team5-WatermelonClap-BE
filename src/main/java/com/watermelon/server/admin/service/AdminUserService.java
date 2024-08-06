package com.watermelon.server.admin.service;

import com.watermelon.server.admin.exception.AdminNotAuthorizedException;
import com.watermelon.server.admin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    public void authorize(String uid){
        if(!adminUserRepository.existsByUid(uid)) throw new AdminNotAuthorizedException();
    }

}
