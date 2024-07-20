package com.example.reservation.client;

import com.example.reservation.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user", url = "localhost:8080/user")
public interface UserClient {

    @GetMapping("/getCustomerInfo")
    ResponseEntity<CustomerDto> getInfo
            (@RequestHeader("X-AUTH-TOKEN") String token);
}