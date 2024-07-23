package com.watermelon.server.orderevent.repository;

import com.watermelon.server.orderevent.domain.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderEventRepository extends JpaRepository<OrderEvent,Long> {

    @Query("SELECT e FROM OrderEvent e WHERE e.startDate <= :date AND e.endDate >= :date")
    Optional<OrderEvent> findByDateBetween(@Param("date") LocalDateTime date);


    @Query("SELECT e FROM OrderEvent e WHERE e.quiz.id = :quizId AND e.id = :eventId")
    Optional<OrderEvent> findByIdAndQuizId(@Param("eventId") Long eventId, @Param("quizId") Long quizId);
}
