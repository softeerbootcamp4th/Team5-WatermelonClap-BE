package com.watermelon.server.randomevent.service;

import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotteryServiceImpl implements LotteryService{

    private final int NOT_RANKED = -1;

    private final ParticipantRepository participantRepository;

    @Override
    public List<ResponseLotteryWinnerDto> getLotteryWinners() {
        return participantRepository.findByLotteryRankNot(NOT_RANKED).stream()
                .map(participant ->  ResponseLotteryWinnerDto.from(
                        participant.getEmail(),
                        participant.getLotteryRank())
                )
                .collect(Collectors.toList());
    }
}
