package com.watermelon.server.event.order.dto.request;


import com.watermelon.server.event.order.domain.OrderEvent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventWinnerRequestDto {

    private static final Logger log = LoggerFactory.getLogger(OrderEventWinnerRequestDto.class);
    @NotNull
    @Size(min=11, max=11)
    @Pattern(regexp="^010-\\d{4}-\\d{4}$|^011-\\d{4}-\\d{4}$")
    private String phoneNumber;

    public static OrderEventWinnerRequestDto forTest() {
        return OrderEventWinnerRequestDto.builder()
                .phoneNumber("testPhoneNumber")
                .build();
    }
    public static OrderEventWinnerRequestDto makeWithPhoneNumber(String phoneNumber) {
        return OrderEventWinnerRequestDto.builder()
                .phoneNumber(phoneNumber)
                .build();
    }
    public boolean isPhoneNumberValid() {
        if(phoneNumber.startsWith("010") && phoneNumber.length() == 11){
            return true;
        }
        return false;
    }
}
