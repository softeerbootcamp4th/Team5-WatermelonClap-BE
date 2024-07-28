package com.watermelon.server.admin.controller;


import com.watermelon.server.admin.service.AdminOrderService;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventWinnerDto;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {
    // 변환 작업 필요


    private final AdminOrderService adminOrderService;
    @PostMapping("/event/order")
    public void makeOrderEvent(RequestOrderEventDto requestOrderEventDto){
        adminOrderService.makeOrderEvent(requestOrderEventDto);
    }

    @GetMapping("/event/order")
    public List<ResponseOrderEventDto> getOrderEventForAdmin(){
        return adminOrderService.getOrderEventsForAdmin();
    }

    @GetMapping("/event/order/{eventId}/winner")
    public List<ResponseOrderEventWinnerDto> getOrderEventWinnersForAdmin(@PathVariable("eventId") Long eventId) throws WrongOrderEventFormatException {
        return adminOrderService.getOrderEventWinnersForAdmin(eventId);
    }




}
