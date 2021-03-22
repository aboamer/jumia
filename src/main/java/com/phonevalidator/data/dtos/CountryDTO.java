package com.phonevalidator.data.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDTO {

    private String countryName;
    private String countryCode;
    private String regex;
}
