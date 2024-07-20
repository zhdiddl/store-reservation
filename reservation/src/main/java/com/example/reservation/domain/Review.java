package com.example.reservation.domain;

import com.example.reservation.form.AddReviewForm;
import com.example.reservation.form.UpdateReviewForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;
    private Long customerId;
    private Long reservationId;

    @Audited
    private LocalDateTime slotDateTime;

    private String review;
    private double starRating;

    private LocalDateTime updatedAt;

    public static Review of(Long customerId, AddReviewForm addReviewForm) {
        return Review.builder()
                .slotDateTime(addReviewForm.getSlotDateTime())
                .reservationId(addReviewForm.getReservationId())
                .storeId(addReviewForm.getStoreId())
                .customerId(customerId)
                .review(addReviewForm.getReview())
                .starRating(addReviewForm.getStarRating())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Review from(UpdateReviewForm updateReviewForm) {
        return Review.builder()
                .review(updateReviewForm.getReview())
                .starRating(updateReviewForm.getStarRating())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
