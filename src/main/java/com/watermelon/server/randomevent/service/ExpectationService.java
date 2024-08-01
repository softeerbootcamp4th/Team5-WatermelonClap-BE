package com.watermelon.server.randomevent.service;

import com.watermelon.server.randomevent.domain.Expectation;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.dto.request.RequestExpectationDto;
import com.watermelon.server.randomevent.dto.response.ResponseExpectationDto;
import com.watermelon.server.randomevent.error.ExpectationAlreadyExistError;
import com.watermelon.server.randomevent.repository.ExpectationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpectationService {
    private final ExpectationRepository expectationRepository;
    private final LotteryService lotteryService;

    public void makeExpectation(String uid,RequestExpectationDto requestExpectationDto)
            throws ExpectationAlreadyExistError {
        LotteryApplier lotteryApplier = lotteryService.findLotteryApplierByUid(uid);
        if(isExpectationAlreadyExist(lotteryApplier)) throw new ExpectationAlreadyExistError();
        Expectation expectation = Expectation.makeExpectation(requestExpectationDto,lotteryApplier);
        expectationRepository.save(expectation);
    }

    private static boolean isExpectationAlreadyExist(LotteryApplier lotteryApplier) {
        return lotteryApplier.getExpectation() != null;
    }

    public List<ResponseExpectationDto> getExpectationsForUser() {
        List<Expectation> expectations =
                expectationRepository
                        .findTop30ByIsApprovedTrueOrderByCreatedAtDesc();
        List<ResponseExpectationDto> responseExpectationDtos = new ArrayList<>();
        expectations.forEach(expectation -> {
            responseExpectationDtos.add(ResponseExpectationDto.forUser(expectation));
        });
        return responseExpectationDtos;
    }
}
