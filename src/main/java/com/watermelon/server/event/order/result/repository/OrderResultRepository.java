package com.watermelon.server.event.order.result.repository;


import com.watermelon.server.event.order.result.domain.OrderResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderResultRepository extends JpaRepository<OrderResult, Long> {
}
