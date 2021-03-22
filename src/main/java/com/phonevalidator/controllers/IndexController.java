package com.phonevalidator.controllers;

import com.phonevalidator.data.dtos.CustomerDTO;
import com.phonevalidator.data.dtos.RequestDTO;
import com.phonevalidator.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class IndexController {

    private CustomerService customerService;

    public IndexController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/")
    public String index(Model model, RequestDTO requestDTO)
    {
        List<CustomerDTO> customerDTOS = customerService.findAll(requestDTO);
        model.addAttribute("customersList", customerDTOS);
        return"index";
    }
}