package com.watermelon.server.randomevent.parts.dto.response;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.parts.domain.LotteryApplierParts;
import com.watermelon.server.randomevent.parts.domain.Parts;
import com.watermelon.server.randomevent.parts.domain.PartsCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseMyPartsListDtoTest {

    @Test
    @DisplayName("카테고리 별로 분류한다.")
    void createDtoListByCategory() {

        //given
        Parts parts1 = Parts.builder()
                .category(PartsCategory.COLOR)
                .build();

        Parts parts2 = Parts.builder()
                .category(PartsCategory.WHEEL)
                .build();

        LotteryApplier lotteryApplier = new LotteryApplier();

        LotteryApplierParts lotteryApplierParts1 = LotteryApplierParts.createApplierParts(true, lotteryApplier, parts1);
        LotteryApplierParts lotteryApplierParts2 = LotteryApplierParts.createApplierParts(true, lotteryApplier, parts2);

        List<ResponseMyPartsListDto> expected = List.of(
                ResponseMyPartsListDto.builder()
                        .category(PartsCategory.COLOR.toString())
                        .parts(List.of(ResponsePartsListDto.from(parts1, true)))
                        .build(),
                ResponseMyPartsListDto.builder()
                        .category(PartsCategory.REAR.toString())
                        .parts(List.of())
                        .build(),
                ResponseMyPartsListDto.builder()
                        .category(PartsCategory.DRIVE_MODE.toString())
                        .parts(List.of())
                        .build(),
                ResponseMyPartsListDto.builder()
                        .category(PartsCategory.WHEEL.toString())
                        .parts(List.of(ResponsePartsListDto.from(parts2, true)))
                        .build()
        );

        //when
        List<ResponseMyPartsListDto> actual = ResponseMyPartsListDto.createDtoListByCategory(
                List.of(lotteryApplierParts1, lotteryApplierParts2)
        );

        //then
        assertThat(actual).isEqualTo(expected);

    }
}