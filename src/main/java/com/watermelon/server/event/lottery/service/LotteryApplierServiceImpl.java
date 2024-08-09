package com.watermelon.server.event.lottery.service;

import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.repository.LotteryApplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LotteryApplierServiceImpl implements LotteryApplierService{

    private final LotteryApplierRepository lotteryApplierRepository;

    @Override
    public void applyPartsLotteryApplier(LotteryApplier lotteryApplier) {
        lotteryApplier.applyPartsLottery();
        lotteryApplierRepository.save(lotteryApplier);
    }
}
