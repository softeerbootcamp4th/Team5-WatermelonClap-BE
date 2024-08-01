package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.Expectation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpectationRepository extends JpaRepository<Expectation, Long> {

    List<Expectation> findAllByApprovedIsFalse();
    List<Expectation> findTop30ByApprovedIsTrueOrderByCreatedAtDesc();
}
