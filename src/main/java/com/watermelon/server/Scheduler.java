package com.watermelon.server;

import com.watermelon.server.fifoevent.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final OrderEventService orderEventService;

    @Scheduled(fixedRate = 1000)
    public void checkEventStart(){
        log.info("Checking events by scheduled");
        orderEventService.changeOrderStatusByTime();
    }
}
