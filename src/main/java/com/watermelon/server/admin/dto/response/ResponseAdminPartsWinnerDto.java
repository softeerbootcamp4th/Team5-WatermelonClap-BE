package com.watermelon.server.admin.dto.response;

import com.watermelon.server.event.lottery.domain.AdminCheckStatus;
import com.watermelon.server.event.lottery.domain.LotteryApplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAdminPartsWinnerDto {

    private String uid;
    private String name;
    private String phoneNumber;
    private String address;
    private int rank;
    private AdminCheckStatus status;

    public static ResponseAdminPartsWinnerDto createTestDto() {

        return ResponseAdminPartsWinnerDto.builder()
                .uid("uid")
                .name("name")
                .phoneNumber("phoneNumber")
                .address("address")
                .rank(1)
                .status(AdminCheckStatus.READY)
                .build();

    }

    public static ResponseAdminPartsWinnerDto from(LotteryApplier lotteryApplier){

        return ResponseAdminPartsWinnerDto.builder()
                .uid(lotteryApplier.getUid())
                .name(lotteryApplier.getName())
                .phoneNumber(lotteryApplier.getPhoneNumber())
                .address(lotteryApplier.getAddress())
                .rank(1)
                .status(AdminCheckStatus.getStatus(lotteryApplier.isPartsWinnerCheckedByAdmin()))
                .build();

    }

}