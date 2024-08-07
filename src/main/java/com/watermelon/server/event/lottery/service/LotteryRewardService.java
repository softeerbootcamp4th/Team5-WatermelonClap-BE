package com.watermelon.server.event.lottery.service;

import com.watermelon.server.event.lottery.dto.response.ResponseRewardInfoDto;

public interface LotteryRewardService {

    /**
     * rank 에 대한 경품 정보를 반환한다.
     * @param rank 순위
     * @return 경품 정보
     */
    ResponseRewardInfoDto getRewardInfo(int rank);

}
