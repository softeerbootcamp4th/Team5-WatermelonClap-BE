package com.watermelon.server.randomevent.service;

import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;

import java.util.List;

public interface LotteryService {

    List<ResponseLotteryWinnerDto> getLotteryWinners();

    ResponseLotteryWinnerInfoDto getLotteryWinnerInfo(String uid);

}
