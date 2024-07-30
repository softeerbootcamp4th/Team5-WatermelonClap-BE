package com.watermelon.server.randomevent.parts.service;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.parts.domain.LotteryApplierParts;
import com.watermelon.server.randomevent.parts.domain.Parts;
import com.watermelon.server.randomevent.parts.domain.PartsCategory;
import com.watermelon.server.randomevent.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.randomevent.parts.exception.PartsDrawLimitExceededException;
import com.watermelon.server.randomevent.parts.repository.PartsRepository;
import com.watermelon.server.randomevent.service.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.watermelon.server.Constants.TEST_UID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartsServiceImplTest {

    @Mock
    private PartsRepository partsRepository;

    @Mock
    private LotteryService lotteryService;

    @Mock
    private LotteryApplierPartsService lotteryApplierPartsService;

    @InjectMocks
    private PartsServiceImpl partsService;

    @Test
    @DisplayName("remain chance 가 존재한다면 존재하는 파츠 중 랜덤으로 하나를 뽑아서 저장하고 반환한다.")
    void drawParts() {

        //given
        LotteryApplier applier = LotteryApplier.builder()
                .remainChance(1)
                .build();

        Parts parts = Parts.builder()
                .category(PartsCategory.COLOR)
                .build();

        when(lotteryService.applyAndGet(TEST_UID)).thenReturn(applier);
        when(partsRepository.findAll()).thenReturn(List.of(parts));
        when(lotteryApplierPartsService.addPartsAndGet(eq(applier), eq(parts)))
                .thenReturn(LotteryApplierParts.createApplierParts(true, applier, parts));

        // When
        ResponsePartsDrawDto response = partsService.drawParts(TEST_UID);

        // Then
        assertEquals(parts.getName(), response.getName());
        assertTrue(response.isEquipped());

        verify(lotteryService, times(1)).applyAndGet(TEST_UID);
        verify(lotteryApplierPartsService, times(1)).addPartsAndGet(eq(applier), eq(parts));

    }

    @Test
    @DisplayName("remain chance 가 존재하지 않으면 예외를 발생한다.")
    void drawPartsFailureCase(){

        //given
        LotteryApplier applier = LotteryApplier.builder()
                .remainChance(0)
                .build();

        when(lotteryService.applyAndGet(TEST_UID)).thenReturn(applier);

        //when & then
        assertThrows(PartsDrawLimitExceededException.class, () -> partsService.drawParts(TEST_UID));

    }
}