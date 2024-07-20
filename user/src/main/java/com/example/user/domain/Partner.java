package com.example.user.domain;

import com.example.domain.domain.BaseEntity;
import com.example.user.form.SignUpForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Partner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String phone;
    private String password;
    private String name;
    private String email;


    public static Partner from(SignUpForm form, PasswordEncoder passwordEncoder) {
        return Partner.builder()
                .phone(form.getPhone())
                .password(passwordEncoder.encode(form.getPassword()))
                .name(form.getName())
                .email(form.getEmail())
                .build();
    }
}
