package com.watermelon.server.lottery.parts.service;

import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.parts.domain.LotteryApplierParts;
import com.watermelon.server.event.lottery.parts.domain.Parts;
import com.watermelon.server.event.lottery.parts.domain.PartsCategory;
import com.watermelon.server.event.lottery.parts.repository.LotteryApplierPartsRepository;
import com.watermelon.server.event.lottery.parts.repository.PartsRepository;
import com.watermelon.server.event.lottery.parts.service.LotteryApplierPartsServiceImpl;
import com.watermelon.server.event.lottery.service.LotteryApplierService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

//TODO 구현필요
@ExtendWith(MockitoExtension.class)
class LotteryApplierPartsServiceImplTest {

    @Mock
    private LotteryApplierPartsRepository lotteryApplierPartsRepository;

    @Mock
    private PartsRepository partsRepository;

    @Mock
    private LotteryApplierService lotteryApplierService;

    @InjectMocks
    private LotteryApplierPartsServiceImpl lotteryApplierPartsService;

    @Test
    @DisplayName("만약 응모자가 가지고 있는 기존 파츠 중 신규 파츠가 카테고리의 첫 번째 파츠라면, 장착함.")
    void addPartsAndGetFirstPartsCase() {

        final PartsCategory CATEGORY2 = PartsCategory.COLOR;
        final PartsCategory CATEGORY3 = PartsCategory.WHEEL;

        //given (응모자가 가지고 있는 기존 파츠 중 신규 파츠가 카테고리의 첫 파츠일 때)
        final LotteryApplier lotteryApplier = LotteryApplier.builder()
                .id(anyLong())
                .build();

        final Parts existParts2 = Parts.builder()
                .category(CATEGORY2)
                .build();

        final Parts newParts = Parts.builder()
                .category(CATEGORY3)
                .build();

        final List<LotteryApplierParts> lotteryApplierPartsList = List.of(
                LotteryApplierParts.createApplierParts(true, lotteryApplier, existParts2)
        );

        Mockito.when(lotteryApplierPartsRepository.findLotteryApplierPartsByLotteryApplierId(lotteryApplier.getId()))
                .thenReturn(lotteryApplierPartsList);

        ArgumentCaptor<LotteryApplierParts> captor = ArgumentCaptor.forClass(LotteryApplierParts.class);

        //when (해당 메소드를 호출하면)
        lotteryApplierPartsService.addPartsAndGet(lotteryApplier, newParts);

        //then (장착된 파츠를 저장한다.)
        verify(lotteryApplierPartsRepository).save(captor.capture());
        LotteryApplierParts capturedArgument = captor.getValue();
        assertThat(capturedArgument.isEquipped()).isTrue();

    }

    @Test
    @DisplayName("첫 번째 파츠가 아니라면, 장착하지 않음.")
    void addPartsAndGetNotFirstPartsCase() {

        //given

        //when (응모자가 가지고 있는 기존 파츠 중 신규 파츠가 카테고리의 첫 파츠가 아닐 때)
        final LotteryApplier lotteryApplier = LotteryApplier.builder().id(anyLong()).build();
        final Parts existParts = Parts.builder().category(PartsCategory.COLOR).build();
        final Parts newParts = Parts.builder().category(PartsCategory.COLOR).build();

        final List<LotteryApplierParts> lotteryApplierPartsList = List.of(
                LotteryApplierParts.createApplierParts(true, lotteryApplier, existParts)
        );

        Mockito.when(lotteryApplierPartsRepository.findLotteryApplierPartsByLotteryApplierId(lotteryApplier.getId()))
                .thenReturn(lotteryApplierPartsList);

        ArgumentCaptor<LotteryApplierParts> captor = ArgumentCaptor.forClass(LotteryApplierParts.class);

        //when (해당 메소드를 호출하면)
        lotteryApplierPartsService.addPartsAndGet(lotteryApplier, newParts);

        //then (장착되지 않은 파츠를 저장한다.)
        verify(lotteryApplierPartsRepository).save(captor.capture());
        LotteryApplierParts capturedArgument = captor.getValue();
        assertThat(capturedArgument.isEquipped()).isFalse();
    }

    @Test
    @DisplayName("모든 종류의 파츠를 모으면 파츠 응모권을 부여한다.")
    void addPartsAndGetHasAllPartsCase() {

        //given
        LotteryApplier lotteryApplier = Mockito.mock(LotteryApplier.class);
        Parts parts = Mockito.mock(Parts.class);
        Mockito.when(partsRepository.countCategoryDistinct()).thenReturn(4L);
        Mockito.when(lotteryApplierPartsRepository.countDistinctPartsCategoryByLotteryApplier(lotteryApplier))
                .thenReturn(4L);

        //when
        lotteryApplierPartsService.addPartsAndGet(lotteryApplier, parts);

        //then
        verify(lotteryApplierService).applyPartsLotteryApplier(lotteryApplier);

    }

    @Test
    @DisplayName("모든 종류의 파츠를 모으지 않으면 파츠 응모권을 부여하지 않는다.")
    void addPartsAndGetHasNotAllPartsCase() {

        //given
        LotteryApplier lotteryApplier = Mockito.mock(LotteryApplier.class);
        Parts parts = Mockito.mock(Parts.class);
        Mockito.when(partsRepository.countCategoryDistinct()).thenReturn(4L);
        Mockito.when(lotteryApplierPartsRepository.countDistinctPartsCategoryByLotteryApplier(lotteryApplier))
                .thenReturn(3L);

        //when
        lotteryApplierPartsService.addPartsAndGet(lotteryApplier, parts);

        //then
        verify(lotteryApplierService, never()).applyPartsLotteryApplier(lotteryApplier);

    }


}