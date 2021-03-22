package com.phonevalidator.data.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PhoneNumberState {

    VALID(0), INVALID(1);

    private final Integer value;
}
