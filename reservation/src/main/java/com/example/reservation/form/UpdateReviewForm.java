package com.example.reservation.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewForm {
    private Long storeId;
    private Long reviewId;
    private String review;
    private double starRating;
}
