package com.watermelon.server.event.lottery.parts.service;

import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.parts.domain.LotteryApplierParts;
import com.watermelon.server.event.lottery.parts.domain.Parts;
import com.watermelon.server.event.lottery.parts.repository.LotteryApplierPartsRepository;
import com.watermelon.server.event.lottery.parts.repository.PartsRepository;
import com.watermelon.server.event.lottery.service.LotteryApplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LotteryApplierPartsServiceImpl implements LotteryApplierPartsService{

    private final LotteryApplierPartsRepository lotteryApplierPartsRepository;
    private final PartsRepository partsRepository;
    private final LotteryApplierService lotteryApplierService;

    @Override
    public LotteryApplierParts addPartsAndGet(LotteryApplier lotteryApplier, Parts parts) {

        boolean isFirst = isFirstPartsInCategory(lotteryApplier, parts);

        LotteryApplierParts lotteryApplierParts = lotteryApplierPartsRepository.save(
                LotteryApplierParts.createApplierParts(isFirst, lotteryApplier, parts)
        );

        //만약 모든 카테고리의 파츠를 모았다면
        if(hasAllCategoriesParts(lotteryApplier)) {
            //파츠 응모 처리 후 저장
            lotteryApplierService.applyPartsLotteryApplier(lotteryApplier);
        }

        return lotteryApplierParts;

    }

    boolean hasAllCategoriesParts(LotteryApplier lotteryApplier){
        long partsCount = partsRepository.countCategoryDistinct();
        long lotteryApplierDistinctCount = lotteryApplierPartsRepository.countDistinctPartsCategoryByLotteryApplier(lotteryApplier);
        return lotteryApplierDistinctCount == partsCount;
    }

    @Transactional // 상태를 읽고 쓰는 과정에 원자성 보장 필요
    @Override
    public void toggleEquipped(String uid, Long partsId) {
        LotteryApplierParts lotteryApplierParts = lotteryApplierPartsRepository
                .findLotteryApplierPartsByLotteryApplierUidAndPartsId(uid, partsId).orElseThrow();

        lotteryApplierParts.toggleEquipped();

        lotteryApplierPartsRepository.save(lotteryApplierParts);
    }
    @Override
    public List<LotteryApplierParts> getListByApplier(String uid) {
        return lotteryApplierPartsRepository.findLotteryApplierPartsByLotteryApplierUid(uid);
    }

    /**
     * @param applier 응모자
     * @param parts   파츠
     * @return parts 카테고리의 파츠 첫 번째로 뽑았는지 여부
     */
    private boolean isFirstPartsInCategory(LotteryApplier applier, Parts parts) {
        return lotteryApplierPartsRepository.findLotteryApplierPartsByLotteryApplierId(applier.getId())
                .stream()
                .map(LotteryApplierParts::getParts)
                .noneMatch(parts1 -> parts1.getCategory().equals(parts.getCategory()));
    }

}
