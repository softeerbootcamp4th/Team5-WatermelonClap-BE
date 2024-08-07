package com.watermelon.server.event.lottery.parts.repository;

import com.watermelon.server.event.lottery.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsRepository extends JpaRepository<Parts, Long> {

}
