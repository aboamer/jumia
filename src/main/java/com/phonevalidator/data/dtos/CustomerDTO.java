package com.phonevalidator.data.dtos;

import com.phonevalidator.data.enums.PhoneNumberState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {

    private String name;
    private String phone;
    private String country;
    private PhoneNumberState phoneNumberState;
}
