package com.example.user.repository;

import com.example.user.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByIdAndPhone(Long id, String phone);
}
