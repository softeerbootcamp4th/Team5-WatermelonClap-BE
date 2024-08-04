package com.watermelon.server.randomevent.link.service;

import com.watermelon.server.randomevent.domain.Link;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.link.dto.MyLinkDto;
import com.watermelon.server.randomevent.link.repository.LinkRepository;
import com.watermelon.server.randomevent.service.LotteryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.watermelon.server.Constants.TEST_LINK;
import static com.watermelon.server.Constants.TEST_UID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    @Mock
    private LotteryService lotteryService;

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkServiceImpl linkService;


    @Test
    @DisplayName("응모자에 대한 링크를 반환한다.")
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

    @Test
    @DisplayName("링크의 viewCount 가 1 증가한다.")
    void addLinkViewCount() {

        //given
        Link link = Link.createLink(Mockito.mock(LotteryApplier.class));
        int originalViewCount = link.getViewCount();
        Mockito.when(linkRepository.findByLink(TEST_LINK)).thenReturn(Optional.ofNullable(link));

        //when
        linkService.addLinkViewCount(TEST_LINK);
        int newViewCount = link.getViewCount();

        //then
        Mockito.verify(linkRepository).save(link);
        Assertions.assertThat(newViewCount).isEqualTo(originalViewCount+1);
    }
}