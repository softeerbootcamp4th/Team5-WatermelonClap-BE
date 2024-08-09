package com.watermelon.server.event.lottery.parts.service;

import com.watermelon.server.admin.dto.response.ResponseAdminPartsWinnerDto;
import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.link.service.LinkService;
import com.watermelon.server.event.lottery.parts.domain.PartsReward;
import com.watermelon.server.event.lottery.parts.dto.response.ResponseMyPartsListDto;
import com.watermelon.server.event.lottery.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.event.lottery.parts.dto.response.ResponseRemainChanceDto;
import com.watermelon.server.event.lottery.parts.exception.PartsNotExistException;
import com.watermelon.server.event.lottery.parts.domain.LotteryApplierParts;
import com.watermelon.server.event.lottery.parts.domain.Parts;
import com.watermelon.server.event.lottery.parts.repository.PartsRepository;
import com.watermelon.server.event.lottery.parts.repository.PartsRewardRepository;
import com.watermelon.server.event.lottery.repository.LotteryApplierRepository;
import com.watermelon.server.event.lottery.service.LotteryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartsServiceImpl implements PartsService {

    private final PartsRepository partsRepository;
    private final PartsRewardRepository partsRewardRepository;
    private final LotteryApplierRepository lotteryApplierRepository;
    private final LotteryService lotteryService;
    private final LotteryApplierPartsService lotteryApplierPartsService;
    private final LinkService linkService;

    @Override
    @Transactional //remain chance 를 조회하는 쿼리와 파츠를 저장하는 쿼리 원자성 보장 필요
    public ResponsePartsDrawDto drawParts(String uid) {

        //applier 조회 및 응모 처리
        LotteryApplier applier = lotteryService.applyAndGet(uid);

        //응모 횟수 감소, 없으면 예외 반환.
        applier.drawParts();

        //파츠 뽑기
        Parts parts = getRandomParts();

        //파츠 추가
        LotteryApplierParts lotteryApplierParts = lotteryApplierPartsService.addPartsAndGet(applier, parts);

        return ResponsePartsDrawDto.from(parts, lotteryApplierParts.isEquipped());
    }

    @Override
    public void toggleParts(String uid, Long partId) {
        lotteryApplierPartsService.toggleEquipped(uid, partId);
    }

    public ResponseRemainChanceDto getRemainChance(String uid) {
        return ResponseRemainChanceDto.create(lotteryService.getRemainChance(uid));
    }
  
    public List<ResponseMyPartsListDto> getMyParts(String uid) {
        return ResponseMyPartsListDto.createDtoListByCategory(
                lotteryApplierPartsService.getListByApplier(uid)
        );
    }

    @Override
    public List<ResponseMyPartsListDto> getPartsList(String linkKey) {
        LotteryApplier lotteryApplier = linkService.getApplierByLinkKey(linkKey);
        return getMyParts(lotteryApplier.getUid());
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

    @Transactional //처음 상태와 변경 후 상태의 원자성 보장 필요.
    @Override
    public void partsWinnerCheck(String uid) {
        LotteryApplier lotteryApplier = lotteryApplierRepository.findByUid(uid).orElseThrow();
        lotteryApplier.partsWinnerCheck();
        lotteryApplierRepository.save(lotteryApplier);
    }

    @Override
    public List<ResponseAdminPartsWinnerDto> getAdminPartsWinners() {
        return lotteryApplierRepository.findByIsPartsWinnerTrue().stream()
                .map(ResponseAdminPartsWinnerDto::from)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void partsLottery() {
        List<LotteryApplier> candidates = getPartsLotteryCandidates();
        partsLotteryForCandidates(candidates);
    }

    private List<LotteryApplier> getPartsLotteryCandidates(){
        return lotteryApplierRepository.findByIsPartsApplierTrue();
    }

    private void partsLotteryForCandidates(List<LotteryApplier> candidates) {

        //참가자를 무작위로 섞는다.
        Collections.shuffle(candidates);

        //전체 보상 정보를 가져온다.
        List<PartsReward> rewards = partsRewardRepository.findAll();

        //당첨자를 저장할 리스트
        List<LotteryApplier> lotteryWinners = new ArrayList<>();

        int all_count=0;

        //당첨 정보를 설정하고, 당첨자 인원만큼 리스트에 담는다.
        for(PartsReward reward : rewards){
            int winnerCount = reward.getWinnerCount();
            for(int i=0; i<winnerCount; i++, all_count++){
                LotteryApplier winner = candidates.get(all_count);
                winner.partsLotteryWin();
                lotteryWinners.add(winner);
            }
        }

        lotteryApplierRepository.saveAll(lotteryWinners);

    }


}
