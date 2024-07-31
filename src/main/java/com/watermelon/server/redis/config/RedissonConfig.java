package com.watermelon.server.redis.config;

import com.watermelon.server.event.order.result.domain.OrderResult;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@ConfigurationProperties(prefix = "application-dev-db")
@Configuration
public class RedissonConfig {
    private static final String REDISSON_HOST_PREFIX = "redis://";

    @Bean
    public RedissonClient redissonClient(@Value("${spring.data.redis.host}") String host,
                                         @Value("${spring.data.redis.port}") String port)
    {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(REDISSON_HOST_PREFIX + host + ":" + port);

        return Redisson.create(config);

    }
    @Bean
    public RSet<OrderResult> orderResultSet(RedissonClient redissonClient) {
        return redissonClient.getSet("order-result");
    }

}
