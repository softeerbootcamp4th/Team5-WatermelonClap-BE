package com.watermelon.server.token;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtPayload {

    private String eventId;

    public static JwtPayload from(String orderEventId) {
        return JwtPayload.builder().eventId(orderEventId).build();
    }
}
