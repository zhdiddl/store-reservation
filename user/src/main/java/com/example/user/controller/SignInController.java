package com.example.user.controller;

import com.example.user.form.SignInForm;
import com.example.user.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/signIn")
public class SignInController {
    private final SignInService signInService;

    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody SignInForm signInForm) {
        return ResponseEntity.ok(signInService.CustomerSignInWithToken(signInForm));
    }
    @PostMapping("/partner")
    public ResponseEntity<String> signInPartner(@RequestBody SignInForm signInForm) {
        return ResponseEntity.ok(signInService.PartnerSignInWithToken(signInForm));
    }
}
