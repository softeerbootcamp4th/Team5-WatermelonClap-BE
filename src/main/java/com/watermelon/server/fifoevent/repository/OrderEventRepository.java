package com.watermelon.server.fifoevent.repository;

import com.watermelon.server.fifoevent.domain.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderEventRepository extends JpaRepository<OrderEvent,Long> {

    @Query("SELECT e FROM OrderEvent e WHERE e.startTime <= :date AND e.endTime >= :date")
    Optional<OrderEvent> findByDateBetween(@Param("date") LocalDateTime date);
}
