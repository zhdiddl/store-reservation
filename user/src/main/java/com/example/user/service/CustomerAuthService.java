package com.example.user.service;

import com.example.user.domain.Customer;
import com.example.user.form.SignUpForm;
import com.example.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerAuthService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isPhoneExist(String phone) {
        return customerRepository.findByPhone(phone.trim()).isPresent();
    }

    public void SignUp(SignUpForm signUpForm) {
        customerRepository.save(Customer.from(signUpForm, passwordEncoder));
    }

    public Optional<Customer> findValidCustomer(String phone, String password) {
        return customerRepository.findByPhone(phone.trim())
                .filter(customer -> passwordEncoder.matches(password, customer.getPassword()));
    }


}
