package com.watermelon.server.admin.controller;


import com.watermelon.server.admin.exception.S3ImageFormatException;
import com.watermelon.server.admin.service.AdminOrderEventService;
import com.watermelon.server.admin.service.S3ImageService;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventWinnerDto;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminOrderEventController {
    // 변환 작업 필요
    private final AdminOrderEventService adminOrderEventService;
    @PostMapping("/event/order")
    public ResponseOrderEventDto makeOrderEvent(
            @RequestPart("orderEvent") @Valid RequestOrderEventDto requestOrderEventDto,
            @RequestPart("rewardImage") MultipartFile rewardImage,
            @RequestPart("quizImage") MultipartFile quizImage)
            throws S3ImageFormatException {

        return adminOrderEventService.makeOrderEvent(requestOrderEventDto,rewardImage,quizImage);
    }

    @GetMapping("/event/order")
    public List<ResponseOrderEventDto> getOrderEventForAdmin(){
        return adminOrderEventService.getOrderEventsForAdmin();
    }

    @GetMapping("/event/order/{eventId}/winner")
    public List<ResponseOrderEventWinnerDto> getOrderEventWinnersForAdmin(@PathVariable("eventId") Long eventId) throws WrongOrderEventFormatException {
        return adminOrderEventService.getOrderEventWinnersForAdmin(eventId);
    }
    @ExceptionHandler(S3ImageFormatException.class)
    public ResponseEntity<String> handlePhoneNumberNotExistException(S3ImageFormatException s3ImageFormatException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s3ImageFormatException.getMessage());
    }

}
