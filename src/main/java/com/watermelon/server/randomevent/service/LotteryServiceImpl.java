package com.watermelon.server.randomevent.service;

import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseAdminPartsWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryRankDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseRewardInfoDto;
import com.watermelon.server.randomevent.repository.LotteryApplierRepository;
import com.watermelon.server.randomevent.repository.LotteryRewardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotteryServiceImpl implements LotteryService {

    private final int NOT_RANKED = -1;

    private final LotteryApplierRepository lotteryApplierRepository;
    private final LotteryRewardRepository lotteryRewardRepository;

    @Override
    public List<ResponseLotteryWinnerDto> getLotteryWinners() {
        return lotteryApplierRepository.findByLotteryRankNot(NOT_RANKED).stream()
                .map(participant -> ResponseLotteryWinnerDto.from(
                        participant.getEmail(),
                        participant.getLotteryRank())
                )
                .collect(Collectors.toList());
    }

    @Override
    public ResponseLotteryWinnerInfoDto getLotteryWinnerInfo(String uid) {
        return ResponseLotteryWinnerInfoDto.from(
                lotteryApplierRepository.findByUid(uid).orElseThrow()
        );
    }

    @Override
    public void createLotteryWinnerInfo(String uid, RequestLotteryWinnerInfoDto requestLotteryWinnerInfoDto) {
        LotteryApplier lotteryApplier = lotteryApplierRepository.findByUid(uid).orElseThrow();
        lotteryApplier.setLotteryWinnerInfo(
                requestLotteryWinnerInfoDto.getAddress(),
                requestLotteryWinnerInfoDto.getName(),
                requestLotteryWinnerInfoDto.getPhoneNumber()
        );
        lotteryApplierRepository.save(lotteryApplier);
    }

    @Override
    public ResponseLotteryRankDto getLotteryRank(String uid) {
        return ResponseLotteryRankDto.from(
                lotteryApplierRepository.findByUid(uid).orElseThrow()
        );
    }

    @Override
    public LotteryApplier applyAndGet(String uid) {
        LotteryApplier applier = lotteryApplierRepository.findByUid(uid).orElseThrow();
        if(applier.isLotteryApplier()) return applier;
        applier.applyLottery();
        return lotteryApplierRepository.save(applier);
    }

    @Override
    public int getRemainChance(String uid) {
        return lotteryApplierRepository.findByUid(uid).orElseThrow().getRemainChance();
    }

    @Override
    public ResponseRewardInfoDto getRewardInfo(int rank) {
        return ResponseRewardInfoDto.from(
                lotteryRewardRepository.findLotteryRewardByRank(rank).orElseThrow()
        );
    }

    @Override
    public Page<ResponseLotteryApplierDto> getApplierInfoPage(Pageable pageable) {
        return lotteryApplierRepository.findByIsLotteryApplierTrue(pageable)
                .map(ResponseLotteryApplierDto::from);
    }

    @Override
    public List<ResponseAdminLotteryWinnerDto> getAdminLotteryWinners() {
        return lotteryApplierRepository.findByLotteryRankNot(NOT_RANKED).stream()
                .map(ResponseAdminLotteryWinnerDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseAdminPartsWinnerDto> getAdminPartsWinners() {
        return lotteryApplierRepository.findByIsPartsWinnerTrue().stream()
                .map(ResponseAdminPartsWinnerDto::from)
                .collect(Collectors.toList());
    }

    @Transactional //처음 상태와 변경 후 상태의 원자성 보장 필요.
    @Override
    public void lotteryWinnerCheck(String uid) {
        LotteryApplier lotteryApplier = lotteryApplierRepository.findByUid(uid).orElseThrow();
        lotteryApplier.lotteryWinnerCheck();
        lotteryApplierRepository.save(lotteryApplier);
    }

    @Transactional //처음 상태와 변경 후 상태의 원자성 보장 필요.
    @Override
    public void partsWinnerCheck(String uid) {
        LotteryApplier lotteryApplier = lotteryApplierRepository.findByUid(uid).orElseThrow();
        lotteryApplier.partsWinnerCheck();
        lotteryApplierRepository.save(lotteryApplier);
    }
}
