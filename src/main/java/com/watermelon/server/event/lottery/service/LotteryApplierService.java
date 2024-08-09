package com.watermelon.server.event.lottery.service;

import com.watermelon.server.event.lottery.domain.LotteryApplier;

public interface LotteryApplierService {

    /**
     * 파츠 추첨에 응모한다.
     * @param lotteryApplier 파츠 추첨 응모 대상 응모자
     */
    void applyPartsLotteryApplier(LotteryApplier lotteryApplier);

}