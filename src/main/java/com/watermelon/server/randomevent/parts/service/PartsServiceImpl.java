package com.watermelon.server.randomevent.parts.service;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.parts.domain.LotteryApplierParts;
import com.watermelon.server.randomevent.parts.domain.Parts;
import com.watermelon.server.randomevent.parts.dto.response.ResponseMyPartsListDto;
import com.watermelon.server.randomevent.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.randomevent.parts.dto.response.ResponseRemainChanceDto;
import com.watermelon.server.randomevent.parts.exception.PartsNotExistException;
import com.watermelon.server.randomevent.parts.repository.PartsRepository;
import com.watermelon.server.randomevent.service.LotteryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartsServiceImpl implements PartsService {

    private final PartsRepository partsRepository;
    private final LotteryService lotteryService;
    private final LotteryApplierPartsService lotteryApplierPartsService;

    @Override
    @Transactional //remain chance 를 조회하는 쿼리와 파츠를 저장하는 쿼리 원자성 보장 필요
    public ResponsePartsDrawDto drawParts(String uid) {

        //applier 조회 및 응모 처리
        LotteryApplier applier = lotteryService.applyAndGet(uid);

        //찬스가 남았는지 검사
        applier.hasRemainChanceOrThrow();

        //파츠 뽑기
        Parts parts = getRandomParts();

        //파츠 추가
        LotteryApplierParts lotteryApplierParts = lotteryApplierPartsService.addPartsAndGet(applier, parts);

        return ResponsePartsDrawDto.from(parts, lotteryApplierParts.isEquipped());
    }

    @Override

    public ResponseRemainChanceDto getRemainChance(String uid) {
        return ResponseRemainChanceDto.create(lotteryService.getRemainChance(uid));
    }
    public List<ResponseMyPartsListDto> getMyParts(String uid) {
        return ResponseMyPartsListDto.createDtoListByCategory(
                lotteryApplierPartsService.getListByApplier(uid)
        );
    }

    /**
     * 저장되어 있는 파츠 중 랜덤으로 하나를 반환한다.
     *
     * @return 랜덤으로 선정된 파츠
     */
    private Parts getRandomParts() {
        List<Parts> partsList = partsRepository.findAll();

        if (partsList.isEmpty()) throw new PartsNotExistException();

        int randomIndex = (int) (Math.random() * partsList.size());

        return partsList.get(randomIndex);
    }

}
