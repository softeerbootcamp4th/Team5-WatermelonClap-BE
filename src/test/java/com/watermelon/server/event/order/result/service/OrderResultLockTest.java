package com.watermelon.server.event.order.result.service;

import com.watermelon.server.event.order.result.domain.OrderResult;
import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;





@SpringBootTest
class OrderResultLockTest {

    @Autowired
    OrderResultQueryService orderResultQueryService;
    @Autowired
    OrderResultRepository orderResultRepository;
    @Autowired
    OrderResultCommandService orderResultCommandService;
    @BeforeEach
    void setUp() {
        orderResultRepository.deleteAll();

    }

    @Test
    void 선착순_이벤트_락_적용_100명() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for(int i=0;i<numberOfThreads;i++){
            executorService.submit(()->{
                try{
                    orderResultCommandService
                            .saveResponseResultWithLock(
                                    OrderResult.makeOrderEventApply("testOrderResult"));
                }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        System.out.println("OrderResult 개수: "+orderResultRepository.findAll().size());
        Assertions.assertThat(orderResultQueryService.isOrderApplyNotFull()).isFalse();

    }
    @Test
    void 선착순_이벤트_락_미적용_100명() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for(int i=0;i<numberOfThreads;i++){
            executorService.submit(()->{
                try{
                    orderResultCommandService
                            .saveResponseResultWithOutLock(
                                    OrderResult.makeOrderEventApply("testOrderResult"));
                }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        System.out.println("OrderResult 개수: "+orderResultRepository.findAll().size());
        Assertions.assertThat(orderResultQueryService.isOrderApplyNotFull()).isFalse();

    }

}