package com.example.user.domain;

import com.example.domain.domain.BaseEntity;
import com.example.user.form.SignUpForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String phone;
    private String password;
    private String name;
    private String email;

    public static Customer from(SignUpForm form, PasswordEncoder passwordEncoder) {
        return Customer.builder()
                .phone(form.getPhone())
                .password(passwordEncoder.encode(form.getPassword()))
                .name(form.getName())
                .email(form.getEmail())
                .build();
    }
}
