package com.watermelon.server.fifoevent.service;


import com.watermelon.server.fifoevent.domain.FifoEvent;
import com.watermelon.server.fifoevent.domain.Quiz;
import com.watermelon.server.fifoevent.dto.request.RequestAnswerDto;
import com.watermelon.server.fifoevent.dto.request.RequestFiFoEventDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizResultDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizResultSuccessDto;
import com.watermelon.server.fifoevent.repository.FifoEventRepository;
import com.watermelon.server.fifoevent.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FifoEventService {
    private final FifoEventRepository fifoEventRepository;
    private final QuizRepository quizRepository;


    public ResponseQuizDto getQuiz(){
        Optional<FifoEvent> fifoEventOptional = fifoEventRepository.findByDateBetween(LocalDateTime.now());
        if(!fifoEventOptional.isPresent()){return null;}
        FifoEvent fifoEvent = fifoEventOptional.get();
        return ResponseQuizDto.from(fifoEvent.getQuiz(),fifoEvent.getId());
    }
    public void makeEvent(RequestFiFoEventDto requestFiFoEventDto){
        System.out.println(requestFiFoEventDto);
        FifoEvent newFifoEvent = FifoEvent.makeFifoEvent(requestFiFoEventDto);
        fifoEventRepository.save(newFifoEvent);
    }
    public ResponseQuizResultDto applyFifoEvent(RequestAnswerDto requestAnswerDto){
        Optional<FifoEvent> fifoEventOptional = fifoEventRepository.findById(requestAnswerDto.getFifoEventId());
        FifoEvent fifoEvent = fifoEventOptional.get();

        // 시간 틀림
        if(!fifoEvent.isTimeInEventTime(LocalDateTime.now())){
            return ResponseQuizResultDto.eventTimeOutResult();
        }
        Quiz quiz = fifoEvent.getQuiz();


        //정답 틀림
        if(!quiz.isCorrect(requestAnswerDto.getAnswer())){
            return ResponseQuizResultDto.wrongAnswerResult();
        }

        //선착순 틀림
        if(!fifoEvent.isWinnerAddable()){
            return ResponseQuizResultDto.noMoreWinnerResult();
        }

        //선착순 토큰 발급
        fifoEvent.addWinner();
        return ResponseQuizResultDto.answerRightResult(UUID.randomUUID().toString());


    }

}
