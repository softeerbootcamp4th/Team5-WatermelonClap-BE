package com.watermelon.server.event.lottery.parts.repository;

import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.parts.domain.LotteryApplierParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LotteryApplierPartsRepository extends JpaRepository<LotteryApplierParts, Long> {

    List<LotteryApplierParts> findLotteryApplierPartsByLotteryApplierId(Long lotteryApplierId);
    List<LotteryApplierParts> findLotteryApplierPartsByLotteryApplierUid(String lotteryApplierUid);

    Optional<LotteryApplierParts> findLotteryApplierPartsByLotteryApplierUidAndPartsId(String lotteryApplierUid,Long partsId);

    long countDistinctPartsByLotteryApplier(LotteryApplier lotteryApplier);

}