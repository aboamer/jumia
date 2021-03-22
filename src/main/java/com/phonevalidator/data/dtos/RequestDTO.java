package com.phonevalidator.data.dtos;

import com.phonevalidator.data.enums.PhoneNumberState;
import lombok.Data;

import java.util.List;

@Data
public class RequestDTO {

    private List<String> countries;
    private PhoneNumberState state;
}
