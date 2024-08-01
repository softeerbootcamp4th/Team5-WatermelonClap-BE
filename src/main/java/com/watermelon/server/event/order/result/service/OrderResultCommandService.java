package com.watermelon.server.event.order.result.service;


import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.result.domain.OrderResult;
import com.watermelon.server.redis.annotation.RedisDistributedLock;
import com.watermelon.server.token.ApplyTokenProvider;
import com.watermelon.server.token.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderResultCommandService {
//    private final OrderResultRepository orderResultRepository;
    private final OrderResultQueryService orderResultQueryService;
    private final ApplyTokenProvider applyTokenProvider;
    private final RSet<OrderResult> orderResultSet;

    @Transactional
    public ResponseApplyTicketDto isOrderResultFullElseMake(Long orderEventId){
        String applyToken = applyTokenProvider.createTokenByOrderEventId(JwtPayload.from(String.valueOf(orderEventId )));
        OrderResult orderResult = OrderResult.makeOrderEventApply(applyToken);
        if(saveResponseResultWithLock(orderResult)){
            return ResponseApplyTicketDto.applySuccess(applyToken);
        }
        return ResponseApplyTicketDto.fullApply();
    }

    @Transactional
    public boolean saveResponseResultWithOutLock(OrderResult orderResult){
        if(orderResultQueryService.isOrderApplyNotFull()){
            orderResultSet.add(orderResult);
            return true;
        }
        return false;
    }
    @RedisDistributedLock(key = "orderResultLock")
    public boolean saveResponseResultWithLock(OrderResult orderResult){
        if(orderResultQueryService.isOrderApplyNotFull()){
            orderResultSet.add(orderResult);
            return true;
        }
        return false;
    }


    //저장 할시에 확실하게 돌려주어야함 - 하지만 돌려주지 못 할시에는 어떻게?( 로그인이 안 되어있음)

}
