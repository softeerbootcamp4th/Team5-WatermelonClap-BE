package com.watermelon.server.fifoevent.repository;

import com.watermelon.server.fifoevent.domain.FifoEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FifoEventRepository extends JpaRepository<FifoEvent,Long> {

    @Query("SELECT e FROM FifoEvent e WHERE e.startTime <= :date AND e.endTime >= :date")
    Optional<FifoEvent> findByDateBetween(@Param("date") LocalDateTime date);
}
