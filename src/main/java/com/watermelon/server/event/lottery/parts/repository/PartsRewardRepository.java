package com.watermelon.server.event.lottery.parts.repository;

import com.watermelon.server.event.lottery.parts.domain.PartsReward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsRewardRepository extends JpaRepository<PartsReward, Long> {
}
