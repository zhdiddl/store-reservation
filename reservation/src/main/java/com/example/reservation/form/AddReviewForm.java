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
public class AddReviewForm {
    private Long storeId;
    private Long customerId;
    private Long reservationId;
    private LocalDateTime slotDateTime;
    private String review;
    private double starRating;
}
