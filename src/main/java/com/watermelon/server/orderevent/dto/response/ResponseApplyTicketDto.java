package com.watermelon.server.orderevent.dto.response;

import com.watermelon.server.orderevent.domain.ApplyTicketStatus;
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
                .result(String.valueOf(ApplyTicketStatus.PENDING))
                .ApplyTicket(applyTicket)
                .build();
    }
}
