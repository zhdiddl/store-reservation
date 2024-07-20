package com.example.reservation.dto;

import com.example.reservation.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class ReviewDto {

    private Long id;
    private LocalDateTime slotDateTime;
    private Long storeId;
    private String review;
    private double starRating;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .slotDateTime(review.getSlotDateTime())
                .storeId(review.getStoreId())
                .review(review.getReview())
                .starRating(review.getStarRating())
                .build();
    }
}
