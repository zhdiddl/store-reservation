package com.example.reservation.service;

import com.example.reservation.domain.Reservation;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ExceptionCode;
import com.example.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class KioskService {
    private final ReservationRepository reservationRepository;

    @Transactional
    public void completeVisit(String phone, Long slotId) {
        Reservation reservation = reservationRepository.findAllByCustomerPhone(phone).stream()
                .filter(rsv -> rsv.getStoreSlot().getId().equals(slotId))
                .findFirst().orElseThrow(() -> new CustomException(ExceptionCode.RESERVATION_NOT_FOUND));

        if (reservation.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ExceptionCode.RESERVATION_EXPIRED);
        }
        reservation.setVisitCompleted(true);
        reservationRepository.save(reservation);
    }
}
