package com.watermelon.server.randomevent.parts.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponsePartsListDto {

    private String category;
    private Long partsId;
    private String name;
    private String description;
    private String imgSrc;
    private boolean isEquipped;

    public static ResponsePartsListDto any(){
        return ResponsePartsListDto.builder()
                .partsId(1L)
                .category("any")
                .name("any")
                .description("any")
                .imgSrc("any")
                .isEquipped(false)
                .build();
    }

}
