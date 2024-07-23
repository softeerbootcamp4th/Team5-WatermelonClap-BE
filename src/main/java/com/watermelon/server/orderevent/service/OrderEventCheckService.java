package com.watermelon.server.orderevent.service;

/**
 * 선착순 가능 여부를 반환하는 인터페이스
 */
public interface OrderEventCheckService {

    /**
     * 선착순 참가 가능 여부를 반환한다.
     * @param ticket 티켓
     * @return true: 선착순 참가 가능, false: 선착순 참가 불가
     */
    boolean isAble(String ticket);

}
