package com.watermelon.server.event.lottery.repository;

import com.watermelon.server.event.lottery.domain.Expectation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpectationRepository extends JpaRepository<Expectation, Long> {

    List<Expectation> findAllByIsApprovedFalse();

//    @Query("SELECT e FROM Expectation e WHERE e.isApproved = true ORDER BY e.createdAt DESC LIMIT 30")
    List<Expectation> findTop30ByIsApprovedTrueOrderByCreatedAtDesc();
}
