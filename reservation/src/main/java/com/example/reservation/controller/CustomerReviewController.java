package com.example.reservation.controller;

import com.example.reservation.dto.ReviewDto;
import com.example.reservation.form.AddReviewForm;
import com.example.reservation.form.UpdateReviewForm;
import com.example.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/review")
public class CustomerReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ReviewDto> createReview(
            @RequestHeader("X-AUTH-TOKEN") String token,
            @RequestBody AddReviewForm addReviewForm) {
        return ResponseEntity.ok(ReviewDto.from(reviewService.createReview(token, addReviewForm)));
    }

    @PutMapping("/update")
    public ResponseEntity<ReviewDto> updateReview(
            @RequestHeader("X-AUTH-TOKEN") String token,
            @RequestBody UpdateReviewForm updateReviewForm) {
        return ResponseEntity.ok(ReviewDto.from(reviewService.updateReview(token, updateReviewForm)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReview(
            @RequestHeader("X-AUTH-TOKEN") String token,
            @RequestParam Long reviewId) {
        reviewService.deleteReview(token, reviewId);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
