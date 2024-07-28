package com.watermelon.server.event.order.repository;

import com.watermelon.server.event.order.domain.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderEventRepository extends JpaRepository<OrderEvent,Long> {

    @Query("SELECT e FROM OrderEvent e WHERE e.startDate <= :date AND e.endDate >= :date")
    Optional<OrderEvent> findByDateBetween(@Param("date") LocalDateTime date);


    @Query("SELECT e FROM OrderEvent e join fetch e.orderEventWinner where e.id = :eventId")
    Optional<OrderEvent> findByIdFetchWinner(@Param("eventId") Long eventId);

    @Query("SELECT e FROM OrderEvent e WHERE e.quiz.id = :quizId AND e.id = :eventId")
    Optional<OrderEvent> findByIdAndQuizId(@Param("eventId") Long eventId, @Param("quizId") Long quizId);
}
