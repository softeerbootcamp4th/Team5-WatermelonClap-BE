package com.watermelon.server.fifoevent.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
public class ResponseQuizResultFailDto extends ResponseQuizResultDto {
    private String message;

    public ResponseQuizResultFailDto(boolean isSuccess, String message) {
        super(isSuccess);
        this.message = message;
    }
}
