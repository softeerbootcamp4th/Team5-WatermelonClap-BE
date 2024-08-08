package com.watermelon.server.event.lottery.service;

import com.watermelon.server.event.lottery.dto.response.ResponseRewardInfoDto;
import com.watermelon.server.event.lottery.repository.LotteryRewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LotteryRewardServiceImpl implements LotteryRewardService{

    private final LotteryRewardRepository lotteryRewardRepository;

    @Override
    public ResponseRewardInfoDto getRewardInfo(int rank) {
        return ResponseRewardInfoDto.from(
                lotteryRewardRepository.findLotteryRewardByLotteryRank(rank).orElseThrow()
        );
    }

}
