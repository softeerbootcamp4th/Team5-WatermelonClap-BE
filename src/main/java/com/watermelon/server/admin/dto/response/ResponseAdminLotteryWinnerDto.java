package com.watermelon.server.admin.dto.response;

import com.watermelon.server.event.lottery.domain.AdminCheckStatus;
import com.watermelon.server.event.lottery.domain.LotteryApplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAdminLotteryWinnerDto {

    private String uid;
    private String name;
    private String phoneNumber;
    private String address;
    private int rank;
    private AdminCheckStatus status;

    public static ResponseAdminLotteryWinnerDto createTestDto() {

        return ResponseAdminLotteryWinnerDto.builder()
                .uid("uid")
                .name("name")
                .phoneNumber("phoneNumber")
                .address("address")
                .rank(1)
                .status(AdminCheckStatus.READY)
                .build();

    }

    public static ResponseAdminLotteryWinnerDto from(LotteryApplier lotteryApplier){

        return ResponseAdminLotteryWinnerDto.builder()
                .uid(lotteryApplier.getUid())
                .name(lotteryApplier.getName())
                .phoneNumber(lotteryApplier.getPhoneNumber())
                .address(lotteryApplier.getAddress())
                .rank(lotteryApplier.getLotteryRank())
                .status(AdminCheckStatus.getStatus(lotteryApplier.isLotteryWinnerCheckedByAdmin()))
                .build();

    }

}