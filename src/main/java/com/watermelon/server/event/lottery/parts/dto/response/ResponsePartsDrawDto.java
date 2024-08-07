package com.watermelon.server.event.lottery.parts.dto.response;

import com.watermelon.server.event.lottery.parts.domain.Parts;
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

    public static ResponsePartsDrawDto from(Parts parts, boolean isEquipped){
        return ResponsePartsDrawDto.builder()
                .category(parts.getCategory().toString())
                .partsId(parts.getId())
                .name(parts.getName())
                .imgSrc(parts.getImgSrc())
                .description(parts.getDescription())
                .isEquipped(isEquipped)
                .build();
    }

}
