package com.watermelon.server.admin.controller;

import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.randomevent.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminLotteryController {

    private final LotteryService lotteryService;

    @GetMapping("admin/event/applier")
    public Page<ResponseLotteryApplierDto> getLotteryAppliers(
            Pageable pageable
    ) {
        return lotteryService.getApplierInfoPage(pageable);
    }

    @GetMapping("admin/lotteries")
    public ResponseEntity<List<ResponseAdminLotteryWinnerDto>> getLotteryWinners() {
        return new ResponseEntity<>(lotteryService.getAdminLotteryWinners(), HttpStatus.OK);
    }

}
