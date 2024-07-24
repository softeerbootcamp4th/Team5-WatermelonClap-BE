package com.watermelon.server.orderevent.service;

import com.watermelon.server.orderevent.dto.response.ResponseOrderEventResultDto;

/**
 * 선착순 가능 여부를 반환하는 인터페이스
 */
public interface OrderEventCheckService {

    /**
     * 선착순 신청 결과를 반환한다.
     * @param ticket 티켓
     * @param eventId 이벤트 아이디
     * @param quizId 퀴즈 아이디
     * @param answer 정답
     * @return 결과
     */
    ResponseOrderEventResultDto getOrderEventResult(String ticket,  Long eventId, Long quizId, String answer);

}
