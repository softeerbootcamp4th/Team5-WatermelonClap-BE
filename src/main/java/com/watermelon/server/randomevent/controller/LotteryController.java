package com.watermelon.server.randomevent.controller;

import com.watermelon.server.randomevent.auth.annotations.Uid;
import com.watermelon.server.randomevent.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryRankDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseRewardInfoDto;
import com.watermelon.server.randomevent.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping
    public ResponseEntity<List<ResponseLotteryWinnerDto>> getLotteryWinnerList(){
        return new ResponseEntity<>(lotteryService.getLotteryWinners(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseLotteryWinnerInfoDto> getLotteryWinnerInfo(
            @Uid String uid
    ){
        return new ResponseEntity<>(lotteryService.getLotteryWinnerInfo(uid), HttpStatus.OK);
    }

    @PostMapping("/info")
    public ResponseEntity<Void> createLotteryWinnerInfo(
            @RequestBody RequestLotteryWinnerInfoDto dto,
            @Uid String uid
    ){
        lotteryService.createLotteryWinnerInfo(uid, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/rank")
    public ResponseEntity<ResponseLotteryRankDto> getLotteryRank(
            @Uid String uid
    ){
        try {
            return new ResponseEntity<>(lotteryService.getLotteryRank(uid), HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(ResponseLotteryRankDto.createNotApplied(), HttpStatus.OK);
        }

    }

    @GetMapping("/reward/{rank}")
    public ResponseEntity<ResponseRewardInfoDto> getRewardInfo(
            @PathVariable int rank
    ){
        return new ResponseEntity<>(lotteryService.getRewardInfo(rank), HttpStatus.OK);
    }

}
