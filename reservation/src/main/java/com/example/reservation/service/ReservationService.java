package com.example.reservation.service;

import com.example.reservation.client.UserClient;
import com.example.reservation.domain.Reservation;
import com.example.reservation.domain.StoreSlot;
import com.example.reservation.dto.CustomerDto;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ExceptionCode;
import com.example.reservation.form.AddReservationForm;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.StoreSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserClient userClient;
    private final StoreSlotRepository storeSlotRepository;

    @Transactional(readOnly = true)
    public Reservation getReservation(Long reservationId, Long customerId) {
        return reservationRepository.findById(reservationId)
                .filter(reservation -> reservation.getCustomerId().equals(customerId))
                .orElseThrow(() -> new CustomException(ExceptionCode.RESERVATION_NOT_FOUND));
    }

    @Transactional
    public Reservation createReservation(String token, AddReservationForm addReservationForm) {

        // 고객 정보 확인
        CustomerDto customerDto = userClient.getInfo(token).getBody();
        if (customerDto == null) {
            throw new CustomException(ExceptionCode.CUSTOMER_NOT_FOUND);
        }

        // 매칭되는 슬롯 확인
        StoreSlot matchedStoreSlot = storeSlotRepository.findById(addReservationForm.getSlotId())
                .orElseThrow(() -> new CustomException(ExceptionCode.SLOT_NOT_FOUND));

        // 예약 가능한 시간의 슬롯이면 슬롯 개수 차감 처리
        if (matchedStoreSlot.getDateTime().minusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new CustomException(ExceptionCode.SLOT_NOT_ALLOWED_FOR_RESERVATION);
        } else if (matchedStoreSlot.getQuantity() < addReservationForm.getVisitorCount()) {
            throw new CustomException(ExceptionCode.SLOT_QUANTITY_NOT_ENOUGH);
        }
        matchedStoreSlot.setQuantity(matchedStoreSlot.getQuantity() - addReservationForm.getVisitorCount());

        // 예약 정보 저장
        return reservationRepository.save(Reservation.of(customerDto.getId(), addReservationForm, matchedStoreSlot));
    }

    @Transactional
    public void cancelReservation(Long reservationId, Long customerId) {
        // 예약 정보 확인
        Reservation reservation = reservationRepository.findById(reservationId)
                .filter(rsv -> rsv.getCustomerId().equals(customerId))
                .orElseThrow(() -> new CustomException(ExceptionCode.RESERVATION_NOT_FOUND));

        // 매칭되는 슬롯 확인
        StoreSlot matchedStoreSlot = storeSlotRepository.findById(reservation.getStoreSlot().getId())
                .orElseThrow(() -> new CustomException(ExceptionCode.SLOT_NOT_FOUND));

        // 예약 취소 및 슬롯 개수 복구
        if (reservation.getExpiredAt().isAfter(LocalDateTime.now()) || reservation.isVisitCompleted()) {
            throw new CustomException(ExceptionCode.RESERVATION_CANCELLATION_NOT_ALLOWED);
        }
        matchedStoreSlot.setQuantity(matchedStoreSlot.getQuantity() + reservation.getVisitorCount());

        reservationRepository.delete(reservation);
    }
}
