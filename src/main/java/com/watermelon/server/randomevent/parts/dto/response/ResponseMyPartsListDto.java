package com.watermelon.server.randomevent.parts.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseMyPartsListDto {

    private String category;
    List<ResponsePartsListDto> parts;

    public static List<ResponseMyPartsListDto> createTestDtoList() {
        return List.of(
                ResponseMyPartsListDto.builder()
                .category("color")
                .parts(List.of(
                        ResponsePartsListDto.any(),
                        ResponsePartsListDto.any(),
                        ResponsePartsListDto.any()
                ))
                .build(),
        ResponseMyPartsListDto.builder()
                .category("rear")
                .parts(List.of(
                        ResponsePartsListDto.any(),
                        ResponsePartsListDto.any(),
                        ResponsePartsListDto.any()
                ))
                .build()
        );
    }

}
