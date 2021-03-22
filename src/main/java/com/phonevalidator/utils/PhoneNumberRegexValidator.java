package com.phonevalidator.utils;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PhoneNumberRegexValidator {

    public boolean validateNumberWithRegex(String phoneNumber, String regex) {
        return Pattern.matches(regex, phoneNumber);
    }
}
