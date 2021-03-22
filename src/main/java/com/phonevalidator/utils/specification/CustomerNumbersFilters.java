package com.phonevalidator.utils.specification;

import com.phonevalidator.data.dtos.CustomerDTO;
import com.phonevalidator.data.entities.Customer;
import com.phonevalidator.data.enums.PhoneNumberState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CustomerNumbersFilters {

    public static Specification<Customer> countriesFilter(List<String> customerPhones) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(customerPhones)) {
                return criteriaBuilder.conjunction();
            } else {

                List<Predicate> predicates = new ArrayList<>();
                customerPhones.forEach(customerPhone -> {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("phone")),
                            "(" + customerPhone.toLowerCase() + "%"));
                });
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static java.util.function.Predicate<CustomerDTO> isStateValid(PhoneNumberState phoneNumberState) {
        return customerDTO -> phoneNumberState == null || customerDTO.getPhoneNumberState().equals(phoneNumberState);
    }
}
