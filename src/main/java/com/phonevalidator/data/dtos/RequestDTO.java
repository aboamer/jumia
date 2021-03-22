package com.phonevalidator.data.dtos;

import com.phonevalidator.data.enums.PhoneNumberState;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestDTO {

    private List<String> countries;
    private PhoneNumberState state;
}
