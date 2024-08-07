package com.watermelon.server.lottery.repository;

import com.watermelon.server.event.lottery.domain.LotteryReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotteryRewardRepository extends JpaRepository<LotteryReward, Long> {
    Optional<LotteryReward> findLotteryRewardByLotteryRank(int rank);
}
