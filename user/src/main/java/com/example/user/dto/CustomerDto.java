package com.example.user.dto;

import com.example.user.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String phone;

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getPhone());
    }
}
