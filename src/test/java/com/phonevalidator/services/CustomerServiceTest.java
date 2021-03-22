package com.phonevalidator.services;

import com.phonevalidator.data.dtos.CustomerDTO;
import com.phonevalidator.data.dtos.RequestDTO;
import com.phonevalidator.data.entities.Customer;
import com.phonevalidator.data.enums.PhoneNumberState;
import com.phonevalidator.mapper.CustomerMapper;
import com.phonevalidator.repostitories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private CountryService countryService;

    private List<Customer> customers;
    private List<CustomerDTO> customerDTOS;

    @BeforeEach
    public void initObj() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository, customerMapper,countryService);

        customers = new ArrayList<Customer>() {{
            add(Customer.builder().name("Jim").phone("(212) 6007989253").build());
            add(Customer.builder().name("Tom").phone("(258) 847602609").build());
            add(Customer.builder().name("Ali").phone("(212) 698054317").build());
        }};

        customerDTOS = new ArrayList<CustomerDTO>() {{
            add(CustomerDTO.builder().name("Jim").phone("(212) 6007989253").build());
            add(CustomerDTO.builder().name("Tom").phone("(258) 847602609").build());
            add(CustomerDTO.builder().name("Ali").phone("(212) 698054317").build());
        }};

        mockDependenciesActions();
    }

    private void mockDependenciesActions() {
        when(customerRepository.findAll(Mockito.any(Specification.class))).thenReturn(customers);
        when(customerMapper.convertEntitiesToDtos(customers)).thenReturn(customerDTOS);
        when(countryService.isCountryPhoneValid(customerDTOS.get(0).getPhone())).thenReturn(PhoneNumberState.INVALID);
        when(countryService.isCountryPhoneValid(customerDTOS.get(1).getPhone())).thenReturn(PhoneNumberState.VALID);
        when(countryService.isCountryPhoneValid(customerDTOS.get(2).getPhone())).thenReturn(PhoneNumberState.INVALID);
        when(countryService.getCountryByCode(customerDTOS.get(0).getPhone())).thenReturn("Morocco");
        when(countryService.getCountryByCode(customerDTOS.get(1).getPhone())).thenReturn("Mozambique");
        when(countryService.getCountryByCode(customerDTOS.get(2).getPhone())).thenReturn("Morocco");
    }

    @Test
    public void shouldFilterCustomersAndReturnOnlyValidNumbersState() {

        List<CustomerDTO> result = customerService.findAll(RequestDTO.builder().state(PhoneNumberState.VALID).build());

        result.forEach((customerDTO) ->
                Assertions.assertEquals(
                        PhoneNumberState.VALID,
                        customerDTO.getPhoneNumberState()));
    }

    @Test
    public void shouldFilterCustomersAndReturnOnlyMorroccoAndMozambique() {

        List<CustomerDTO> result = customerService.findAll(RequestDTO.builder().state(PhoneNumberState.VALID).build());

        result.forEach((customerDTO) ->
                Assertions.assertEquals(
                        PhoneNumberState.VALID,
                        customerDTO.getPhoneNumberState()));
    }

}
