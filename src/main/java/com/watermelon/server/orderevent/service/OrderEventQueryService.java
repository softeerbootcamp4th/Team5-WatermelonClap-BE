package com.watermelon.server.orderevent.service;


import com.watermelon.server.orderevent.domain.OrderEvent;
import com.watermelon.server.orderevent.domain.Quiz;
import com.watermelon.server.orderevent.dto.request.RequestAnswerDto;
import com.watermelon.server.orderevent.dto.request.RequestOrderEventDto;
import com.watermelon.server.orderevent.dto.response.ResponseOrderEventDto;
import com.watermelon.server.orderevent.dto.response.ResponseQuizResultDto;
import com.watermelon.server.orderevent.repository.OrderEventRepository;
import com.watermelon.server.orderevent.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderEventQueryService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventQueryService.class);
    private final OrderEventRepository orderEventRepository;
    private final QuizRepository quizRepository;


    // OSIV로 요청이 끝날떄까지 데이터베이스 커넥션을 유지하기에 실시간 트래픽이 몰릴 때 커넥션이 모자랄 수 있음
    // (짧으면 괜찮을 수도..하지만 Transcation 밖에서 외부 API를 호출한다면?
    @Transactional(readOnly = true)
    public List<ResponseOrderEventDto> getOrderEvents(){
        List<OrderEvent> orderEvents = orderEventRepository.findAll();
        List<ResponseOrderEventDto> responseOrderEventDtos = new ArrayList<>();
        orderEvents.forEach(orderEvent -> responseOrderEventDtos.add(ResponseOrderEventDto.from(orderEvent)));
        return responseOrderEventDtos;
    }
    @Transactional(readOnly = true)
    public ResponseOrderEventDto getOrderEvent(Long orderEventId){
        Optional<OrderEvent> orderEvent = orderEventRepository.findById(orderEventId);
        return ResponseOrderEventDto.from(orderEvent.get());
    }






}
