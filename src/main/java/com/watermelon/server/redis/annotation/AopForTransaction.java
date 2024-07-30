package com.watermelon.server.redis.annotation;


import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AopForTransaction {

    @Transactional(propagation = Propagation.REQUIRES_NEW) //새로운 트랜잭션 시작, 기존 트랙잭션 중첩 트랙잭션으로 만듬
    public Object proceed
            (
            final ProceedingJoinPoint joinPoint //final로 선언됨으로 새로운 객체로 변경되지 않음
    )
            throws Throwable {
        return joinPoint.proceed(); //어드바이스되는 메소드가 실행됨
    }
}
