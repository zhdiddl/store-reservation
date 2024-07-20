package com.example.reservation.dto;

import com.example.reservation.domain.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String customerPhone;
    private Long storeId;
    private Long slotId;
    private LocalDateTime slotDateTime;

    public static ReservationDto from(Reservation reservation) {
        return ReservationDto.builder()
                .customerPhone(reservation.getCustomerPhone())
                .storeId(reservation.getStoreId())
                .slotId(reservation.getStoreSlot().getId())
                .slotDateTime(reservation.getSlotDateTime())
                .build();
    }
}
