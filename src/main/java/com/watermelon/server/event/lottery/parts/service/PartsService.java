package com.watermelon.server.event.lottery.parts.service;

import com.watermelon.server.admin.dto.response.ResponseAdminPartsWinnerDto;
import com.watermelon.server.event.lottery.parts.dto.response.ResponseMyPartsListDto;
import com.watermelon.server.event.lottery.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.event.lottery.parts.dto.response.ResponseRemainChanceDto;

import java.util.List;

public interface PartsService {

    /**
     * uid 에 대한 유저의 파츠 뽑기를 수행하고 결과를 반환
     * @param uid uid
     * @return 파츠 뽑기 결과
     */
    ResponsePartsDrawDto drawParts(String uid);

    void toggleParts(String uid, Long partId);
  
    ResponseRemainChanceDto getRemainChance(String uid);

    /**
     * uid 에 대한 유저의 파츠 목록을 dto 형식으로 반환
     * @param uid uid
     * @return 유저의 파츠 목록
     */
    List<ResponseMyPartsListDto> getMyParts(String uid);

    /**
     * 링크 키에 대한 유저의 파츠 목록 반환
     * @param linkKey 링크 키
     * @return 링크에 대한 유저의 파츠 목록
     */
    List<ResponseMyPartsListDto> getPartsList(String linkKey);

    /**
     * 파츠 이벤트 응모 인원에 대해 뽑기를 진행한다.
     */
    void partsLottery();

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

}
