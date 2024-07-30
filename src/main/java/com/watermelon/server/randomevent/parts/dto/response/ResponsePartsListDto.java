package com.watermelon.server.randomevent.parts.dto.response;

import com.watermelon.server.randomevent.parts.domain.Parts;
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

    public static ResponsePartsListDto from(Parts parts, boolean isEquipped){
        return ResponsePartsListDto.builder()
                .category(parts.getCategory().toString())
                .partsId(parts.getId())
                .name(parts.getName())
                .description(parts.getDescription())
                .imgSrc(parts.getImgSrc())
                .isEquipped(isEquipped)
                .build();
    }

}
