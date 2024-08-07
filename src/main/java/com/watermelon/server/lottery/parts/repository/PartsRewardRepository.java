package com.watermelon.server.lottery.parts.repository;

import com.watermelon.server.lottery.parts.domain.PartsReward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsRewardRepository extends JpaRepository<PartsReward, Long> {
}
