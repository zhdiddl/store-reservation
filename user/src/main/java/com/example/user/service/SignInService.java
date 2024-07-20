package com.example.user.service;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.user.UserType;
import com.example.user.domain.Customer;
import com.example.user.domain.Partner;
import com.example.user.exception.CustomException;
import com.example.user.exception.ExceptionCode;
import com.example.user.form.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignInService {
    private final CustomerAuthService customerAuthService;
    private final PartnerAuthService partnerAuthService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public String CustomerSignInWithToken(SignInForm signInForm) {
        Customer validCustomer = customerAuthService
                .findValidCustomer(signInForm.getPhone(), signInForm.getPassword())
                .orElseThrow(() -> new CustomException(ExceptionCode.CUSTOMER_NOT_FOUND));

        String token = jwtAuthenticationProvider
                .createToken(validCustomer.getPhone(), validCustomer.getId(), UserType.CUSTOMER);

        return "고객 로그인 및 토큰 생성 완료: " + token;
    }

    public String PartnerSignInWithToken(SignInForm signInForm) {
        Partner validPartner = partnerAuthService
                .findValidPartner(signInForm.getPhone(), signInForm.getPassword())
                .orElseThrow(() -> new CustomException(ExceptionCode.PARTNER_NOT_FOUND));

        String token = jwtAuthenticationProvider
                .createToken(validPartner.getPhone(), validPartner.getId(), UserType.PARTNER);

        return "파트너 로그인 및 토큰 생성 완료: " + token;
    }
}
