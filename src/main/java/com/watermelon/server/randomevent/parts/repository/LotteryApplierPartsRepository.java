package com.watermelon.server.randomevent.parts.repository;

import com.watermelon.server.randomevent.parts.domain.LotteryApplierParts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LotteryApplierPartsRepository extends JpaRepository<LotteryApplierParts, Long> {

    List<LotteryApplierParts> findLotteryApplierPartsByLotteryApplierId(Long lotteryApplierId);
    List<LotteryApplierParts> findLotteryApplierPartsByLotteryApplierUid(String lotteryApplierUid);

    Optional<LotteryApplierParts> findLotteryApplierPartsByLotteryApplierUidAndPartsId(String lotteryApplierUid,Long partsId);

}