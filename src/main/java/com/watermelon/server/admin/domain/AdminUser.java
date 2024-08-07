package com.watermelon.server.admin.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AdminUser extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String uid;

}
