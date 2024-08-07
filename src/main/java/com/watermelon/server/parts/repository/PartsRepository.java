package com.watermelon.server.parts.repository;

import com.watermelon.server.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsRepository extends JpaRepository<Parts, Long> {

}
