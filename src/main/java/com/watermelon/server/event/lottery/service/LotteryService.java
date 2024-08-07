package com.watermelon.server.event.lottery.service;

import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseAdminPartsWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.event.lottery.dto.response.ResponseLotteryRankDto;
import com.watermelon.server.event.lottery.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.event.lottery.dto.response.ResponseLotteryWinnerInfoDto;
import com.watermelon.server.event.lottery.dto.response.ResponseRewardInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LotteryService {

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

    /**
     * uid 를 가진 응모자에 대한 남은 뽑기 횟수를 반환한다.
     * @param uid uid
     * @return 뽑기 횟수
     */
    int getRemainChance(String uid);

    /**
     * rank 에 대한 경품 정보를 반환한다.
     * @param rank 순위
     * @return 경품 정보
     */
    ResponseRewardInfoDto getRewardInfo(int rank);

    /**
     * 응모자 정보를 페이지로 반환
     * @param pageable 페이지 정보
     * @return 응모자 정보 페이지
     */
    Page<ResponseLotteryApplierDto> getApplierInfoPage(Pageable pageable);


    /**
     * 파츠 추첨 당첨자 명단을 반환
     * @return 파츠 추첨 당첨자 리스트
     */
    List<ResponseAdminPartsWinnerDto> getAdminPartsWinners();


    /**
     * uid 에 해당하는 당첨자 파츠 추첨 어드민 확인 상태를 참으로 변경
     * @param uid 당첨자의 uid
     */
    void partsWinnerCheck(String uid);

    /**
     * 추첨 이벤트 응모 인원에 대해 뽑기를 진행한다.
     */
    void lottery();

    /**
     * 파츠 이벤트 응모 인원에 대해 뽑기를 진행한다.
     */
    void partsLottery();


    /*외부에서 LotteryApplier를 찾아오려고 할 떄 사용해야하는 메소드*/
    LotteryApplier findLotteryApplierByUid(String uid);

    /**
     * 추첨 응모자를 등록한다.
     * @param uid 응모자의 uid
     */
    void registration(String uid);

    /**
     * uid를 가진 추첨 응모자의 존재 여부를 반환한다.
     * @param uid 응모자의 uid
     * @return 존재 여부
     */
    boolean isExist(String uid);

}
