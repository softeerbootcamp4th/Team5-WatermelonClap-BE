package com.watermelon.server.randomevent.link.service;

import com.watermelon.server.randomevent.domain.Link;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.link.dto.MyLinkDto;
import com.watermelon.server.randomevent.link.exception.LinkNotFoundException;
import com.watermelon.server.randomevent.link.repository.LinkRepository;
import com.watermelon.server.randomevent.service.LotteryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService{

    private static final Logger log = LoggerFactory.getLogger(LinkServiceImpl.class);
    private final LotteryService lotteryService;
    private final LinkRepository linkRepository;

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

    @Override
    @Transactional
    public void addLinkViewCount(String linkId) {
        Link link = linkRepository.findByLink(linkId).orElseThrow(LinkNotFoundException::new);
        link.addLinkViewCount();
        linkRepository.save(link);
    }
}