package com.watermelon.server.event.order.dto.response;

import com.watermelon.server.event.order.domain.ApplyTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseApplyTicketDto {
    private String result;
    private String ApplyTicket;

    public static ResponseApplyTicketDto from(String applyTicket){
        return ResponseApplyTicketDto.builder()
                .result(ApplyTicketStatus.SUCCESS.name())
                .ApplyTicket(applyTicket)
                .build();
    }
    public static ResponseApplyTicketDto wrongAnswer(){
        return ResponseApplyTicketDto.builder()
                .result(ApplyTicketStatus.WRONG.name())
                .ApplyTicket(null)
                .build();
    }
    public static ResponseApplyTicketDto fullApply(){
        return ResponseApplyTicketDto.builder()
                .result(ApplyTicketStatus.CLOSED.name())
                .ApplyTicket(null)
                .build();
    }
}
