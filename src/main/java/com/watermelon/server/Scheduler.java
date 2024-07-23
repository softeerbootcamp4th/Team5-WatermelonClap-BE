package com.watermelon.server;

import com.watermelon.server.orderevent.service.OrderEventCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final OrderEventCommandService orderEventCommandService;

    @Scheduled(fixedRate = 1000)
    public void checkEventStart(){
        log.info("Checking events by scheduled");
        orderEventCommandService.changeOrderStatusByTime();
    }
}
