package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.Expectation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpectationRepository extends JpaRepository<Expectation, Long> {
}
