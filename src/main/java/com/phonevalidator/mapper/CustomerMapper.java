package com.phonevalidator.mapper;

import com.phonevalidator.data.dtos.CustomerDTO;
import com.phonevalidator.data.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends BaseMapper<Customer, CustomerDTO>{
}
