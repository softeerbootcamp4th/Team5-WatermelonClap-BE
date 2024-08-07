package com.watermelon.server.lottery.auth.service;

import com.watermelon.server.lottery.auth.exception.InvalidTokenException;

/**
 * 토큰을 검사하는 인터페이스
 */
public interface TokenVerifier {
    /**
     * 토큰을 검사한 뒤 그에 해당하는 uid 를 반환한다.
     * 유효하지 않다면 InvalidTokenException 을 던진다.
     * @param token
     * @return uid
     * @throws InvalidTokenException
     */
    String verify(String token) throws InvalidTokenException;

}