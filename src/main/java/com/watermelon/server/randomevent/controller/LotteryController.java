package com.watermelon.server.randomevent.controller;

import com.watermelon.server.randomevent.auth.Utils;
import com.watermelon.server.randomevent.auth.service.TokenVerifier;
import com.watermelon.server.randomevent.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryService lotteryService;
    private final TokenVerifier tokenVerifier;

    @GetMapping("/event/lotteries")
    public ResponseEntity<List<ResponseLotteryWinnerDto>> getLotteryWinnerList(){
        return new ResponseEntity<>(lotteryService.getLotteryWinners(), HttpStatus.OK);
    }

    @GetMapping("/event/lotteries/info")
    public ResponseEntity<ResponseLotteryWinnerInfoDto> getLotteryWinnerInfo(
            @RequestHeader("Authorization") String authorization
    ){

        String uid = tokenVerifier.verify(Utils.parseAuthenticationHeaderValue(authorization));
        return new ResponseEntity<>(lotteryService.getLotteryWinnerInfo(uid), HttpStatus.OK);

    }

    @PostMapping("/event/lotteries/info")
    public ResponseEntity<Void> createLotteryWinnerInfo(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RequestLotteryWinnerInfoDto dto
    ){

        String uid = tokenVerifier.verify(Utils.parseAuthenticationHeaderValue(authorization));
        lotteryService.createLotteryWinnerInfo(uid, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
