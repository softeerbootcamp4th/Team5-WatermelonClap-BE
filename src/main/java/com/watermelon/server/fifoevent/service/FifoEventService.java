package com.watermelon.server.fifoevent.service;


import com.watermelon.server.fifoevent.domain.FifoEvent;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizDto;
import com.watermelon.server.fifoevent.repository.FifoEventRepository;
import com.watermelon.server.fifoevent.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FifoEventService {
    private final QuizRepository quizRepository;
    private final FifoEventRepository fifoEventRepository;


    public ResponseQuizDto getQuiz(){
        Optional<FifoEvent> fifoEvent = fifoEventRepository.findByDate(LocalDateTime.now());
        return ResponseQuizDto.from(fifoEvent.get().getQuiz());
    }
}
