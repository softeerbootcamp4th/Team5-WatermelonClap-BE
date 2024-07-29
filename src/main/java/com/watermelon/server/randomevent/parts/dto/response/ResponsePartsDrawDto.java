package com.watermelon.server.randomevent.parts.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponsePartsDrawDto {

    private String category;
    private Long partsId;
    private String name;
    private String description;
    private String imgSrc;
    private boolean isEquipped;

    public static ResponsePartsDrawDto createResponsePartsDrawDtoTest(){
        return ResponsePartsDrawDto.builder()
                .category("Test")
                .partsId(1L)
                .imgSrc("Test")
                .description("Test")
                .isEquipped(true)
                .build();
    }

}
