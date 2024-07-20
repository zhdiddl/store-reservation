package com.example.user.controller;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.user.UserVo;
import com.example.user.domain.Customer;
import com.example.user.dto.CustomerDto;
import com.example.user.exception.CustomException;
import com.example.user.exception.ExceptionCode;
import com.example.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final CustomerRepository customerRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @GetMapping("/getCustomerInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader("X-AUTH-TOKEN") String token) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);

        Customer customer = customerRepository.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ExceptionCode.CUSTOMER_NOT_FOUND));

        return ResponseEntity.ok(CustomerDto.from(customer));
    }
}
