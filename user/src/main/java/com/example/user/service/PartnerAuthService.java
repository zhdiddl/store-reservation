package com.example.user.service;

import com.example.user.domain.Partner;
import com.example.user.form.SignUpForm;
import com.example.user.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PartnerAuthService {
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isPhoneExist(String phone) {
        return partnerRepository.findByPhone(phone.trim()).isPresent();
    }

    public void SignUp(SignUpForm signUpForm) {
        partnerRepository.save(Partner.from(signUpForm, passwordEncoder));
    }

    public Optional<Partner> findValidPartner(String phone, String password) {
        return partnerRepository.findByPhone(phone.trim())
                .filter(partner -> passwordEncoder.matches(password, partner.getPassword()));
    }
}
