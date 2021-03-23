package com.phonevalidator.services;

import com.phonevalidator.data.enums.CountryEnum;
import com.phonevalidator.data.enums.PhoneNumberState;
import com.phonevalidator.utils.PhoneNumberRegexValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class CountryServiceTest {

    private CountryService countryService;

    @Mock
    private PhoneNumberRegexValidator phoneNumberRegexValidator;

    @BeforeEach
    public void initObj() {
        MockitoAnnotations.initMocks(this);
        countryService = new CountryService(phoneNumberRegexValidator);
    }

    @Test
    public void shouldReturnInvalidForInputCountryPhone() {
        PhoneNumberState phoneNumberState = countryService.isCountryPhoneValid("213422");
        Assertions.assertEquals(PhoneNumberState.INVALID, phoneNumberState);
    }

    @Test
    public void shouldReturnValidForInputCountryPhone() {
        when(phoneNumberRegexValidator.validateNumberWithRegex(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
        PhoneNumberState phoneNumberState = countryService.isCountryPhoneValid("(212) 698054317");
        Assertions.assertEquals(PhoneNumberState.VALID, phoneNumberState);
    }

    @Test
    public void shouldReturnMorroccoForInputCountryPhone() {
        String country = countryService.getCountryByCode("(212) 698054317");
        Assertions.assertEquals(CountryEnum.MOROCCO.getCountryName(), country);
    }

    @Test
    public void shouldReturnEmptyForInputCountryPhone() {
        String country = countryService.getCountryByCode("(235) 698054317");
        Assertions.assertEquals("", country);
    }

    @Test
    public void shouldReturnValidCountryCodeForInputCountryMorocco() {
        String code = countryService.getCodeByCountry(CountryEnum.MOROCCO.getCountryName());
        Assertions.assertEquals(CountryEnum.MOROCCO.getCode(), code);
    }

    @Test
    public void shouldReturnEmptyCountryCodeForInputCountryMorocco() {
        String code = countryService.getCodeByCountry("Italy");
        Assertions.assertEquals("", code);
    }
}
