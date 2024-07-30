package com.watermelon.server.redis.facade;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisFacade {
    private final RedissonClient redisson;

}
