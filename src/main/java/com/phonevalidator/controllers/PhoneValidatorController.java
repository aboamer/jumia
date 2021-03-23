package com.phonevalidator.controllers;

import com.phonevalidator.data.dtos.CustomerDTO;
import com.phonevalidator.data.dtos.RequestDTO;
import com.phonevalidator.services.CustomerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/v1/phone-validator")
public class PhoneValidatorController {

    private CustomerService customerService;

    @Autowired
    public PhoneValidatorController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "The basic api of app - takes optional query parameters for filtration - returns customers numbers with state and country")
    @GetMapping("")
    public ResponseEntity<List<CustomerDTO>> getAllNumbers(RequestDTO requestDTO) {
        return ResponseEntity.ok(customerService.findAll(requestDTO));
    }
}
