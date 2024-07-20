package com.example.reservation.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReservationForm {

    private Long slotId;
    private Long customerId;
    private String customerPhone;
    private Integer visitorCount;
    private LocalDateTime slotDateTime;
}
