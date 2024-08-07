package com.watermelon.server.lottery.parts.dto.response;

import com.watermelon.server.lottery.parts.domain.LotteryApplierParts;
import com.watermelon.server.lottery.parts.domain.Parts;
import com.watermelon.server.lottery.parts.domain.PartsCategory;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 응모자 파츠 리스트를 카테고리 별로 분류해서 반환한다.
     * @param lotteryApplierPartsList 응모자 파츠 리스트
     * @return 응모자의 파츠 리스트
     */
    public static List<ResponseMyPartsListDto> createDtoListByCategory(List<LotteryApplierParts> lotteryApplierPartsList) {

        PartsCategory[] categories = PartsCategory.values();
        Map<PartsCategory, List<ResponsePartsListDto>> map = new HashMap<>();

        for (PartsCategory partsCategory : categories) {
            map.put(partsCategory, new ArrayList<>());
        }

        for(LotteryApplierParts lotteryApplierParts : lotteryApplierPartsList){
            Parts parts = lotteryApplierParts.getParts();
            map.get(parts.getCategory()).add(ResponsePartsListDto.from(parts, lotteryApplierParts.isEquipped()));
        }

        List<ResponseMyPartsListDto> responseMyPartsListDtos = new ArrayList<>();
        for(PartsCategory partsCategory : categories){
            responseMyPartsListDtos.add(
                    new ResponseMyPartsListDto(
                            partsCategory.toString(),
                            map.get(partsCategory)
                    )
            );
        }

        return responseMyPartsListDtos;

    }

}
