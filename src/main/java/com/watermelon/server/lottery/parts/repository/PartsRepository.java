package com.watermelon.server.lottery.parts.repository;

import com.watermelon.server.lottery.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsRepository extends JpaRepository<Parts, Long> {

}
