package com.watermelon.server.admin.controller;

import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.randomevent.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
