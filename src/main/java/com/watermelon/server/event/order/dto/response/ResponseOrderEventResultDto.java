package com.watermelon.server.event.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseOrderEventResultDto {

    public enum Status {

        SUCCESS("success"),
        CLOSED("closed"),
        WRONG("wrong");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    private String result;
    private String reason;

}
