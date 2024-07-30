package com.watermelon.server.randomevent.parts.service;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.parts.domain.LotteryApplierParts;
import com.watermelon.server.randomevent.parts.domain.Parts;
import com.watermelon.server.randomevent.parts.repository.LotteryApplierPartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LotteryApplierPartsServiceImpl implements LotteryApplierPartsService{

    private final LotteryApplierPartsRepository lotteryApplierPartsRepository;

    @Override
    public LotteryApplierParts addPartsAndGet(LotteryApplier lotteryApplier, Parts parts) {

        boolean isFirst = isFirstPartsInCategory(lotteryApplier, parts);

        return lotteryApplierPartsRepository.save(
                LotteryApplierParts.createApplierParts(isFirst, lotteryApplier, parts)
        );

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
