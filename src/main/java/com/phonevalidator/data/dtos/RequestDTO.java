package com.phonevalidator.data.dtos;

import com.phonevalidator.data.enums.PhoneNumberState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    private List<String> countries;
    private PhoneNumberState state;
}
