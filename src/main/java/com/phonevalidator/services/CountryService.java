package com.phonevalidator.services;

import com.phonevalidator.data.dtos.CountryDTO;
import com.phonevalidator.data.enums.CountryEnum;
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
                (phoneNumber, countryDTOSMap.get(extractCountryCodeFromPhone(phoneNumber)).getRegex());
        return valid;
    }

    private String extractCountryCodeFromPhone(String phoneNumber) {
        return StringUtils.substringBetween(phoneNumber, "(", ")");
    }

    private boolean isPhoneNumberOrPhoneCodeValid(String phoneNumber) {
        return StringUtils.isNoneBlank(phoneNumber) && StringUtils.isNoneBlank(extractCountryCodeFromPhone(phoneNumber)) ;
    }

    public String getCountryByCode(String phoneNumber) {
        Map<String, CountryDTO> countryDTOSMap = getCodeCountryMap();
        return isPhoneNumberOrPhoneCodeValid(phoneNumber)? getCountryName(phoneNumber, countryDTOSMap) : "";
    }

    private String getCountryName(String phoneNumber, Map<String, CountryDTO> countryDTOSMap) {
        String code = extractCountryCodeFromPhone(phoneNumber);
        return countryDTOSMap.get(code) == null? "": countryDTOSMap.get(code).getCountryName();
    }

    public String getCodeByCountry(String country) {
        Map<String, CountryDTO> countryDTOSMap = getCountryNameCountryMap();
        return StringUtils.isBlank(country)? "" : getCountryCode(country, countryDTOSMap);
    }

    private String getCountryCode(String country, Map<String, CountryDTO> countryDTOSMap) {
        return (countryDTOSMap.get(country) == null)? "": countryDTOSMap.get(country).getCountryCode();
    }

    private List<CountryDTO> initAndGetCountriesInfo() {
        List<CountryDTO> countryDTOS = new ArrayList<>();

        countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.CAMEROON.getCountryName()).countryCode(CountryEnum.CAMEROON.getCode()).regex(CountryEnum.CAMEROON.getRegex()).build());
        countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.ETHIOPIA.getCountryName()).countryCode(CountryEnum.ETHIOPIA.getCode()).regex(CountryEnum.ETHIOPIA.getRegex()).build());
        countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.MOROCCO.getCountryName()).countryCode(CountryEnum.MOROCCO.getCode()).regex(CountryEnum.MOROCCO.getRegex()).build());
        countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.MOZAMBIQUE.getCountryName()).countryCode(CountryEnum.MOZAMBIQUE.getCode()).regex(CountryEnum.MOZAMBIQUE.getRegex()).build());
        countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.UGANDA.getCountryName()).countryCode(CountryEnum.UGANDA.getCode()).regex(CountryEnum.UGANDA.getRegex()).build());

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
