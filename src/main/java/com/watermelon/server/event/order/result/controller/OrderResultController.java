package com.watermelon.server.event.order.result.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderResultController {

    @PostMapping(path = "/event/order/{eventId}/{quizId}/apply")
    public void makeApply(@RequestHeader("ApplyTicket") String applyTicket,
                          @PathVariable("eventId") String eventId,
                          @PathVariable("quizId") String quizId) {


    }
}
