package com.watermelon.server.fifoevent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseQuizResultSuccessDto extends  ResponseQuizResultDto {
    private String applyToken;

    public ResponseQuizResultSuccessDto(boolean isSuccess, String applyToken) {
        super(isSuccess);
        this.applyToken = applyToken;
    }
}
