package com.watermelon.server.admin.controller;


import com.watermelon.server.admin.service.AdminService;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.service.OrderEventCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {
    // 변환 작업 필요


    private final AdminService adminService;
    @PostMapping("/event/order")
    public void makeOrderEvent(RequestOrderEventDto requestOrderEventDto){
        adminService.makeOrderEvent(requestOrderEventDto);
    }

    @GetMapping("/event/order")
    public List<ResponseOrderEventDto> getOrderEventForAdmin(){
        return adminService.getOrderEventsForAdmin();
    }




}
