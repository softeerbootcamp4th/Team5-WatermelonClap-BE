package com.watermelon.server.admin.dto.response;

import com.watermelon.server.randomevent.domain.AdminCheckStatus;
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

}