package com.watermelon.server.randomevent.service;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryRankDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;

import java.util.List;

public interface LotteryService {

    /**
     * 추첨이벤트 당첨자 리스트를 반환한다.
     * @return 당첨자 리스트
     */
    List<ResponseLotteryWinnerDto> getLotteryWinners();

    /**
     * uid 를 가진 유저에 대한 당첨자 정보를 반환한다.
     * @param uid uid
     * @return 당첨자 정보
     */
    ResponseLotteryWinnerInfoDto getLotteryWinnerInfo(String uid);

    /**
     * uid 를 가진 유저에 대한 당첨자 정보를 저장한다.
     * @param uid uid
     * @param requestLotteryWinnerInfoDto 당첨자 정보
     */
    void createLotteryWinnerInfo(String uid, RequestLotteryWinnerInfoDto requestLotteryWinnerInfoDto);

    /**
     * uid 를 가진 유저에 대한 추첨 이벤트 순위를 반환한다.
     * @param uid
     * @return 순위
     */
    ResponseLotteryRankDto getLotteryRank(String uid);

    /**
     * 응모한 뒤 응모자 객체를 반환. 만약 처음 응모라면, 응모 처리.
     * @param uid
     * @return 응모자
     */
    LotteryApplier applyAndGet(String uid);

}
