package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.Expectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpectationRepository extends JpaRepository<Expectation, Long> {

    List<Expectation> findAllByIsApprovedFalse();

//    @Query("SELECT e FROM Expectation e WHERE e.isApproved = true ORDER BY e.createdAt DESC LIMIT 30")
    List<Expectation> findTop30ByIsApprovedTrueOrderByCreatedAtDesc();
}
