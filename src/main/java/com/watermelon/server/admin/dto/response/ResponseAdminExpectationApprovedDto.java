package com.watermelon.server.admin.dto.response;


import com.watermelon.server.event.lottery.domain.Expectation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseAdminExpectationApprovedDto {

    private boolean isApproved;


    public static ResponseAdminExpectationApprovedDto forAdminAfterToggleIsApproved(Expectation expectation) {
        return ResponseAdminExpectationApprovedDto.builder()
                .isApproved(expectation.isApproved())
                .build();
    }

    @Builder
    public ResponseAdminExpectationApprovedDto(boolean isApproved) {
        this.isApproved = isApproved;
    }
}
