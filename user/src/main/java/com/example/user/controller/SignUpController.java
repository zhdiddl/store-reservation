package com.example.user.controller;

import com.example.user.form.SignUpForm;
import com.example.user.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/signUp")
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping("/customer")
    public ResponseEntity<String> signUpCustomer(@RequestBody SignUpForm signUpForm) {
        return ResponseEntity.ok(signUpService.signUpCustomer(signUpForm));
    }

    @PostMapping("/partner")
    public ResponseEntity<String> signUpPartner(@RequestBody SignUpForm signUpForm) {
        return ResponseEntity.ok(signUpService.signUpPartner(signUpForm));
    }
}
