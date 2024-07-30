package com.watermelon.server.redis.repository;

import com.watermelon.server.event.order.result.domain.OrderResult;
import org.springframework.data.repository.CrudRepository;

public interface OrderResultRedisRepository extends
        CrudRepository<OrderResult,Long> {
}
