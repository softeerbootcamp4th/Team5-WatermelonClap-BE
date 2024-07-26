package com.watermelon.server.randomevent.controller;

import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping("/event/lotteries")
    public ResponseEntity<List<ResponseLotteryWinnerDto>> getLotteryWinnerList(){
        return new ResponseEntity<>(lotteryService.getLotteryWinners(), HttpStatus.OK);
    }

}
