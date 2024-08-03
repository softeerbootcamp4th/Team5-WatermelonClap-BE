package com.watermelon.server.randomevent.link.service;

import com.watermelon.server.randomevent.domain.Link;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.link.dto.MyLinkDto;
import com.watermelon.server.randomevent.service.LotteryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.watermelon.server.Constants.TEST_LINK;
import static com.watermelon.server.Constants.TEST_UID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    @Mock
    private LotteryService lotteryService;

    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    void getMyLink() {

        //given
        LotteryApplier lotteryApplier = LotteryApplier.builder()
                .link(
                        Link.builder()
                                .link(TEST_LINK)
                                .build()
                )
                .build();

        Mockito.when(lotteryService.findLotteryApplierByUid(TEST_UID)).thenReturn(
                lotteryApplier
        );

        MyLinkDto expected = MyLinkDto.create(TEST_LINK);

        //when
        MyLinkDto actual = linkService.getMyLink(TEST_UID);

        //then
        assertEquals(expected, actual);

    }
}