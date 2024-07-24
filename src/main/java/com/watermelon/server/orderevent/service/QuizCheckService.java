package com.watermelon.server.orderevent.service;

/**
 * 퀴즈 ID 에 대한 정답 여부를 반환하는 인터페이스
 */
public interface QuizCheckService {

    /**
     * 퀴즈 ID 에 대한 정답 여부를 반환한다.
     * @param quizId 퀴즈의 ID
     * @param answer 정답
     * @return true: 정답, false: 오답
     */
    boolean isCorrect(Long quizId, String answer);

}