package com.phonevalidator.services;

import com.phonevalidator.data.dtos.CustomerDTO;
import com.phonevalidator.data.dtos.RequestDTO;
import com.phonevalidator.data.entities.Customer;
import com.phonevalidator.mapper.BaseMapper;
import com.phonevalidator.mapper.CustomerMapper;
import com.phonevalidator.repostitories.CustomerRepository;
import com.phonevalidator.utils.specification.CustomerNumbersSpecification;
import com.phonevalidator.utils.specification.SearchCriteria;
import com.phonevalidator.utils.specification.SearchKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private CountryService countryService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, CountryService countryService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.countryService = countryService;
    }

    public List<CustomerDTO> findAll(RequestDTO requestDTO) {
        List<Customer> customers = customerRepository
                .findAll(CustomerNumbersSpecification
                        .countriesFilter(Optional.ofNullable(requestDTO.getCountries())
                                .map(Collection::stream).orElseGet(Stream::empty)
                                .map(country -> countryService.getCodeByCountry(country))
                                .collect(Collectors.toList())));
        List<CustomerDTO> customerDTOS = getCustomerDTOS(customers).stream()
                .filter(CustomerNumbersSpecification.isStateValid(requestDTO.getState()))
                .collect(Collectors.toList());
        return customerDTOS;
    }

    private List<CustomerDTO> getCustomerDTOS(List<Customer> customers) {
        List<CustomerDTO> customerDTOS = customerMapper.toDTOs(customers);
        customerDTOS.stream()
                .map(customerDTO -> {
                    customerDTO.setPhoneNumberState(countryService.isCountryPhoneValid(customerDTO.getPhone()));
                    return customerDTO;
                })
                .map(customerDTO -> {
                    customerDTO.setCountry(countryService.getCountryByCode(customerDTO.getPhone()));
                    return customerDTO;
                }).collect(Collectors.toList());
        return customerDTOS;
    }
}
