package com.watermelon.server.redis.config;


import com.watermelon.server.event.order.result.domain.OrderResult;
import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Profile("local")
@Configuration
public class EmbeddedRedisConfig {
    private RedisServer redisServer;
    private static final String REDISSON_HOST_PREFIX = "redis://localhost:6379";

    public EmbeddedRedisConfig( @Value("${spring.data.redis.port}")int port) throws IOException {
        redisServer = new RedisServer(port);
    }
    @PostConstruct
    private void startRedis() throws IOException {
        try{
            redisServer.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() {
        this.redisServer.stop();
    }

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress(REDISSON_HOST_PREFIX);
        return Redisson.create(config);
    }

    @Bean
    public RSet<OrderResult> orderResultSet(RedissonClient redissonClient) {
        return redissonClient.getSet("order-result");
    }
}
