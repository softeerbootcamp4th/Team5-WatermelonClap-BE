package com.watermelon.server.randomevent.service;

import com.watermelon.server.randomevent.domain.Participant;
import com.watermelon.server.randomevent.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotteryServiceImpl implements LotteryService {

    private final int NOT_RANKED = -1;

    private final ParticipantRepository participantRepository;

    @Override
    public List<ResponseLotteryWinnerDto> getLotteryWinners() {
        return participantRepository.findByLotteryRankNot(NOT_RANKED).stream()
                .map(participant -> ResponseLotteryWinnerDto.from(
                        participant.getEmail(),
                        participant.getLotteryRank())
                )
                .collect(Collectors.toList());
    }

    @Override
    public ResponseLotteryWinnerInfoDto getLotteryWinnerInfo(String uid) {
        return ResponseLotteryWinnerInfoDto.from(
                participantRepository.findByUid(uid).orElseThrow()
        );
    }

    @Override
    public void createLotteryWinnerInfo(String uid, RequestLotteryWinnerInfoDto requestLotteryWinnerInfoDto) {
        Participant participant = participantRepository.findByUid(uid).orElseThrow();
        participant.setLotteryWinnerInfo(
                requestLotteryWinnerInfoDto.getAddress(),
                requestLotteryWinnerInfoDto.getName(),
                requestLotteryWinnerInfoDto.getPhoneNumber()
        );
        participantRepository.save(participant);
    }
}
