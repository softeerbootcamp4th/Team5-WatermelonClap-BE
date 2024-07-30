package com.watermelon.server.randomevent.parts.repository;

import com.watermelon.server.randomevent.parts.domain.LotteryApplierParts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotteryApplierPartsRepository extends JpaRepository<LotteryApplierParts, Long> {

    List<LotteryApplierParts> findLotteryApplierPartsByLotteryApplierId(Long lotteryApplierId);
    List<LotteryApplierParts> findLotteryApplierPartsByLotteryApplierUid(String lotteryApplierUid);

}