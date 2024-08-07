package com.watermelon.server.lottery.repository;

import com.watermelon.server.lottery.domain.Expectation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpectationRepository extends JpaRepository<Expectation, Long> {

    List<Expectation> findAllByIsApprovedFalse();

//    @Query("SELECT e FROM Expectation e WHERE e.isApproved = true ORDER BY e.createdAt DESC LIMIT 30")
    List<Expectation> findTop30ByIsApprovedTrueOrderByCreatedAtDesc();
}
