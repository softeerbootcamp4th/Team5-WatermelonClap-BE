package com.watermelon.server.admin.dto.response;


import com.watermelon.server.randomevent.domain.Expectation;
import com.watermelon.server.randomevent.dto.response.ResponseExpectationDto;
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
