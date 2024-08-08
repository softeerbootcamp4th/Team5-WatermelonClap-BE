package com.watermelon.server.event.lottery.service;

import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.event.lottery.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.event.lottery.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.event.lottery.dto.response.ResponseLotteryWinnerInfoDto;

import java.util.List;

public interface LotteryWinnerService {

    /**
     * 추첨이벤트 당첨자 리스트를 반환한다.
     * @return 당첨자 리스트
     */
    List<ResponseLotteryWinnerDto> getLotteryWinners();

    /**
     * 당첨자 명단을 반환
     * @return 당첨자 리스트
     */
    List<ResponseAdminLotteryWinnerDto> getAdminLotteryWinners();

    /**
     * uid 를 가진 유저에 대한 당첨자 정보를 저장한다.
     * @param uid uid
     * @param requestLotteryWinnerInfoDto 당첨자 정보
     */
    void createLotteryWinnerInfo(String uid, RequestLotteryWinnerInfoDto requestLotteryWinnerInfoDto);


    /**
     * uid 에 해당하는 당첨자 어드민 확인 상태를 참으로 변경
     * @param uid 당첨자의 uid
     */
    void lotteryWinnerCheck(String uid);

    /**
     * uid 를 가진 유저에 대한 당첨자 정보를 반환한다.
     * @param uid uid
     * @return 당첨자 정보
     */
    ResponseLotteryWinnerInfoDto getLotteryWinnerInfo(String uid);


}
