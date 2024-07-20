package com.example.reservation.controller;

import com.example.reservation.domain.Review;
import com.example.reservation.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewSearchController {

    private final ReviewRepository reviewRepository;

    @GetMapping("/store")
    public ResponseEntity<Page<Review>> getAllReviewsByStoreId(
            @RequestParam Long storeId,
            @RequestParam int page,
            @RequestParam int size) {
        if (storeId == null || storeId <= 0) {
            throw new IllegalArgumentException("입력한 가게 ID가 유효하지 않습니다.");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findByStoreId(storeId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getAllReviewsByCustomerId(
            @RequestParam Long customerId) {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("입력한 고객 ID가 유효하지 않습니다.");
        }
        List<Review> reviews = reviewRepository.findAllByCustomerId(customerId);
        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.ok("작성한 리뷰가 없습니다.");
        }
        return ResponseEntity.ok(reviews);
    }
}
