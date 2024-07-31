package com.watermelon.server.event.order.result.service;


import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.result.domain.OrderResult;
import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import com.watermelon.server.redis.annotation.RedisDistributedLock;
import com.watermelon.server.token.ApplyTokenProvider;
import com.watermelon.server.token.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderResultCommandService {
    private final OrderResultRepository orderResultRepository;
    private final OrderResultQueryService orderResultQueryService;
    private final ApplyTokenProvider applyTokenProvider;

    @Transactional
    public ResponseApplyTicketDto isOrderResultFullElseMake(Long orderEventId){
        String applyToken = applyTokenProvider.createTokenByOrderEventId(JwtPayload.from(String.valueOf(orderEventId )));
        OrderResult orderResult = OrderResult.makeOrderEventApply(applyToken);
        if(!orderResultQueryService.isOrderApplyNotFull()){
            orderResultRepository.save(orderResult);
            return ResponseApplyTicketDto.applySuccess(applyToken);
        }
        return ResponseApplyTicketDto.fullApply();
    }


    @Transactional
    public void saveResponseResultWithOutLock(OrderResult orderResult){
        if(!orderResultQueryService.isOrderApplyNotFull()){
            orderResultRepository.save(orderResult);
        }
    }
    @RedisDistributedLock(key = "orderResultLock")
    public void saveResponseResultWithLock(OrderResult orderResult){
        if(!orderResultQueryService.isOrderApplyNotFull()){
            orderResultRepository.save(orderResult);
        }
    }


    //저장 할시에 확실하게 돌려주어야함 - 하지만 돌려주지 못 할시에는 어떻게?( 로그인이 안 되어있음)

}
