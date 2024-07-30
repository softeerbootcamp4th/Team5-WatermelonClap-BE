package com.watermelon.server.randomevent.parts.service;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.parts.domain.LotteryApplierParts;
import com.watermelon.server.randomevent.parts.domain.Parts;

public interface LotteryApplierPartsService {

    /**
     * 응모자에 대해 신규 파츠를 추가함.
     * 만약 응모자가 가지고 있는 기존 파츠 중 신규 파츠가 카테고리의 첫 번째 파츠라면, 장착함.
     * @param lotteryApplier 응모자
     * @param parts 파츠
     * @return 응모자-파츠
     */
    LotteryApplierParts addPartsAndGet(LotteryApplier lotteryApplier, Parts parts);

    void toggleEquipped(String uid, Long partsId);

}
