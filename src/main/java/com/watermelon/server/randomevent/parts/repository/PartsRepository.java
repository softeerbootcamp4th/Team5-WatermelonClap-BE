package com.watermelon.server.randomevent.parts.repository;

import com.watermelon.server.randomevent.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsRepository extends JpaRepository<Parts, Long> {

}
