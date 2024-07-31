package com.watermelon.server.redis.annotation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;


//@RedisDistributedLock 선언시 수행되는 AOP CLASS
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisDistributedLockAop {
    private static final String REDIS_DISTRIBUTED_LOCK_PREFIX = "LOCK:";
    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;


    //@Around: 메소드 호출 자체를 가로챈다.
    @Around("@annotation(com.watermelon.server.redis.annotation.RedisDistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //현재 실행중인 method 객체를 가져온다
        Method method = methodSignature.getMethod();
        RedisDistributedLock redisDistributedLock = method.getAnnotation(RedisDistributedLock.class);
        //해당 메소드의 어노테이션 중에서 RedisDistributed 어노테이션을 가져온다
        String key = REDIS_DISTRIBUTED_LOCK_PREFIX+ redisDistributedLock.key();
        RLock rlock = redissonClient.getLock(key);
        try{
            //Lock을 점유하고 대기해본다
            boolean lockObtainable = rlock.tryLock(redisDistributedLock.waitTime(),
                    redisDistributedLock.waitTime(),
                    redisDistributedLock.timeUnit());
            if(!lockObtainable) return false;

            /*lock을 가질 수 있다면 가로친 메소드를 별개의 트랜잭션을 만들어서 수행한다
            그치만 트랜잭션으로 Blocking이 가능한 것인가?
            왜 굳이 Propagation.Required.new로 새로 트랜잭션을 만들어야 해야하는 것인가*/
            return aopForTransaction.proceed(joinPoint);
        }
        catch (InterruptedException e){
            //다른 스레드에 의해 중단됨
            throw new InterruptedException(e.getMessage());
        }
        finally {
            try{
                rlock.unlock();
            }
            catch(IllegalMonitorStateException e){
                //해당 메소드가 락을 소유하고 있음(락이 이미 해제된 상황에서 해제 시도)
                log.info("Redisson Lock Already UnLock {" +
                        method.getName() +"} {"+
                        key);
            }
        }
    }
}
