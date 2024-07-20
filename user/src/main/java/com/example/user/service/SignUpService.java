package com.example.user.service;

import com.example.user.exception.CustomException;
import com.example.user.exception.ExceptionCode;
import com.example.user.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignUpService {
    private final CustomerAuthService customerAuthService;
    private final PartnerAuthService partnerAuthService;

    public String signUpCustomer(SignUpForm signUpForm) {
        String phone = signUpForm.getPhone();
        if (phone == null || phone.isBlank()) {
            throw new CustomException(ExceptionCode.PHONE_NUMBER_BLANK);
        } else if (customerAuthService.isPhoneExist(phone)) {
            throw new CustomException(ExceptionCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
        customerAuthService.SignUp(signUpForm);
        return "고객 계정 가입 완료";
    }

    public String signUpPartner(SignUpForm signUpForm) {
        String phone = signUpForm.getPhone();

        if (phone == null || phone.isBlank()) {
            throw new CustomException(ExceptionCode.PHONE_NUMBER_BLANK);
        } else if (partnerAuthService.isPhoneExist(phone)) {
            throw new CustomException(ExceptionCode.PHONE_NUMBER_ALREADY_EXISTS);
        }

        partnerAuthService.SignUp(signUpForm);
        return "파트너 계정 가입 완료";
    }

}
