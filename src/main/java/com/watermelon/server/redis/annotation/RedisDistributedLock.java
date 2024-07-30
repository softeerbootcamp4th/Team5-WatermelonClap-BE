package com.watermelon.server.redis.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD) //어노테이션은 메소드에만 적용될 수 있음
@Retention(RetentionPolicy.RUNTIME) //컴파이 +런타임시에도 유지됨
public @interface RedisDistributedLock {


    //분산 락 이름
    String key();
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    //락을 기다리는 기본 시간
    long waitTime() default 5L;

    //락을 임대하는 시간
    long releaseTime() default 3L;
}
