package com.watermelon.server.admin.repository;

import com.watermelon.server.admin.domain.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    boolean existsByUid(String uid);

}
