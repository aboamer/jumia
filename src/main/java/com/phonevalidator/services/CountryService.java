package com.phonevalidator.services;

import com.phonevalidator.data.dtos.CountryDTO;
import com.phonevalidator.data.enums.PhoneNumberState;
import com.phonevalidator.utils.PhoneNumberRegexValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private PhoneNumberRegexValidator phoneNumberRegexValidator;

    @Autowired
    public CountryService(PhoneNumberRegexValidator phoneNumberRegexValidator) {
        this.phoneNumberRegexValidator = phoneNumberRegexValidator;
    }

    public PhoneNumberState isCountryPhoneValid(String phoneNumber) {

        List<Function<String, Boolean>> validations = Arrays.asList(
                phone -> isPhoneNumberOrPhoneCodeValid(phone),
                phone -> isPhoneRegexValid(phone)
        );

        for(Function<String, Boolean> validation: validations) {
            if(!validation.apply(phoneNumber)) {
                return PhoneNumberState.INVALID;
            }
        }

        return PhoneNumberState.VALID;
    }

    private boolean isPhoneRegexValid(String phoneNumber) {
        Map<String, CountryDTO> countryDTOSMap = getCodeCountryMap();
        boolean valid = phoneNumberRegexValidator.validateNumberWithRegex
                (phoneNumber, countryDTOSMap.get(StringUtils.substringBetween(phoneNumber, "(", ")")).getRegex());
        return valid;
    }

    private boolean isPhoneNumberOrPhoneCodeValid(String phoneNumber) {
        return StringUtils.isNoneBlank(phoneNumber) && StringUtils.isNoneBlank(StringUtils.substringBetween(phoneNumber, "(", ")")) ;
    }

    public String getCountryByCode(String phoneNumber) {
        Map<String, CountryDTO> countryDTOSMap = getCodeCountryMap();
        return StringUtils.isBlank(phoneNumber)? "" : countryDTOSMap.get(StringUtils.substringBetween(phoneNumber, "(", ")"))
                .getCountryName();
    }

    public String getCodeByCountry(String country) {
        Map<String, CountryDTO> countryDTOSMap = getCountryNameCountryMap();
        return StringUtils.isBlank(country)? "" : countryDTOSMap.get(country).getCountryCode();
    }

    private List<CountryDTO> initAndGetCountriesInfo() {
        List<CountryDTO> countryDTOS = new ArrayList<>();

        countryDTOS.add(CountryDTO.builder().countryName("Cameroon").countryCode("237").regex("\\(237\\)\\ ?[2368]\\d{7,8}$").build());
        countryDTOS.add(CountryDTO.builder().countryName("Ethiopia").countryCode("251").regex("\\(251\\)\\ ?[1-59]\\d{8}$").build());
        countryDTOS.add(CountryDTO.builder().countryName("Morocco").countryCode("212").regex("\\(212\\)\\ ?[5-9]\\d{8}$").build());
        countryDTOS.add(CountryDTO.builder().countryName("Mozambique").countryCode("258").regex("\\(258\\)\\ ?[28]\\d{7,8}$").build());
        countryDTOS.add(CountryDTO.builder().countryName("Uganda").countryCode("256").regex("\\(256\\)\\ ?\\d{9}$").build());

        return countryDTOS;
    }

    private Map<String, CountryDTO> getCodeCountryMap() {
        return initAndGetCountriesInfo().stream()
                .collect(Collectors.toMap(CountryDTO::getCountryCode, countryDTO -> countryDTO));
    }

    private Map<String, CountryDTO> getCountryNameCountryMap() {
        return initAndGetCountriesInfo().stream()
                .collect(Collectors.toMap(CountryDTO::getCountryName, countryDTO -> countryDTO));
    }
}
