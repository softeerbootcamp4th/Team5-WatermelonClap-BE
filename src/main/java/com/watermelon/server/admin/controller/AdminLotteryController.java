package com.watermelon.server.admin.controller;

import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseAdminPartsWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.event.lottery.parts.service.PartsService;
import com.watermelon.server.event.lottery.service.LotteryService;
import com.watermelon.server.event.lottery.service.LotteryWinnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminLotteryController {

    private final LotteryService lotteryService;
    private final LotteryWinnerService lotteryWinnerService;
    private final PartsService partsService;

    @GetMapping("admin/event/applier")
    public Page<ResponseLotteryApplierDto> getLotteryAppliers(
            Pageable pageable
    ) {
        return lotteryService.getApplierInfoPage(pageable);
    }

    @GetMapping("admin/lotteries")
    public ResponseEntity<List<ResponseAdminLotteryWinnerDto>> getLotteryWinners() {
        return new ResponseEntity<>(lotteryWinnerService.getAdminLotteryWinners(), HttpStatus.OK);
    }

    @GetMapping("admin/event/parts")
    public ResponseEntity<List<ResponseAdminPartsWinnerDto>> getPartsWinners(){
        return new ResponseEntity<>(partsService.getAdminPartsWinners(), HttpStatus.OK);
    }

    @PostMapping("/admin/event/lotteries/{uid}/done")
    public ResponseEntity<Void> lotteryWinnerCheckDone(
            @PathVariable String uid
    ){
        lotteryWinnerService.lotteryWinnerCheck(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/event/parts/{uid}/done")
    public ResponseEntity<Void> partsWinnerCheckDone(
            @PathVariable String uid
    ){
        partsService.partsWinnerCheck(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/event/lotteries")
    public ResponseEntity<Void> lottery() {
        lotteryService.lottery();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/event/parts")
    public ResponseEntity<Void> partsLottery(){
        partsService.partsLottery();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
