package com.example.reservation.repository;

import com.example.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCustomerPhone(String customerPhone);

    List<Reservation> findAllByCustomerId(Long customerId);
}
