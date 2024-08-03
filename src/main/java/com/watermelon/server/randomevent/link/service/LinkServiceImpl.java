package com.watermelon.server.randomevent.link.service;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.link.dto.MyLinkDto;
import com.watermelon.server.randomevent.service.LotteryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService{

    private final LotteryService lotteryService;

    @Transactional
    @Override
    public MyLinkDto getMyLink(String uid) {
        LotteryApplier lotteryApplier =  lotteryService.findLotteryApplierByUid(uid);
        return MyLinkDto.create(
                lotteryApplier
                        .getLink()
                        .getLink()
        );
    }
}