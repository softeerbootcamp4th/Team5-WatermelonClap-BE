package com.watermelon.server.randomevent.parts.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseRemainChanceDto {

    private int remainChance;

    public static ResponseRemainChanceDto createTestDto(){
        return ResponseRemainChanceDto.builder().remainChance(1).build();
    }

}
