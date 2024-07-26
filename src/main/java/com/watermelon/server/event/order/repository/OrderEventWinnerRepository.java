package com.watermelon.server.event.order.repository;

import com.watermelon.server.event.order.domain.OrderEventWinner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventWinnerRepository extends JpaRepository<OrderEventWinner,Long> {
}
