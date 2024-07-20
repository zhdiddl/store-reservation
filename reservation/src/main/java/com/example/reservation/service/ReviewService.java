package com.example.reservation.service;

import com.example.reservation.client.UserClient;
import com.example.reservation.domain.Reservation;
import com.example.reservation.domain.Review;
import com.example.reservation.domain.Store;
import com.example.reservation.dto.CustomerDto;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ExceptionCode;
import com.example.reservation.form.AddReviewForm;
import com.example.reservation.form.UpdateReviewForm;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.ReviewRepository;
import com.example.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserClient userClient;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Review createReview(String token, AddReviewForm addReviewForm) {

        // 고객 정보 확인
        CustomerDto customerDto = userClient.getInfo(token).getBody();
        if (customerDto == null) {
            throw new CustomException(ExceptionCode.CUSTOMER_NOT_FOUND);
        }

        // 리뷰 작성할 예약과 기존 리뷰 유무 확인
        Reservation matchedReservation = reservationRepository.findAllByCustomerId(customerDto.getId()).stream()
                .filter(rsv -> rsv.getStoreId().equals(addReviewForm.getStoreId())
                        && rsv.getSlotDateTime().equals(addReviewForm.getSlotDateTime()))
                .findFirst().orElseThrow(() -> new CustomException(ExceptionCode.RESERVATION_NOT_FOUND));

        if (reviewRepository.findByReservationId(matchedReservation.getId()).isPresent()) {
            throw new CustomException(ExceptionCode.ALREADY_REVIEWED_RESERVATION);
        }

        // 방문 완료 여부 및 시점 확인
        if (!matchedReservation.isVisitCompleted()) {
            throw new CustomException(ExceptionCode.RESERVATION_NOT_COMPLETED);
        } else if (matchedReservation.getSlotDateTime().plusWeeks(1).isBefore(LocalDateTime.now())) {
            throw new CustomException(ExceptionCode.OLD_COMPLETED_RESERVATION);
        }

        // 별점을 가게에 반영
        storeRepository.findById(addReviewForm.getStoreId())
                .ifPresent(store -> store.addRating(addReviewForm.getStarRating()));

        return reviewRepository.save(Review.of(customerDto.getId(), addReviewForm));
    }

    @Transactional
    public Review updateReview(String token, UpdateReviewForm updateReviewForm) {

        // 고객 정보 확인
        CustomerDto customerDto = userClient.getInfo(token).getBody();
        if (customerDto == null) {
            throw new CustomException(ExceptionCode.CUSTOMER_NOT_FOUND);
        }

        // 수정 가능 여부 확인
        Review matchedReview = reviewRepository.findAllByCustomerId(customerDto.getId()).stream()
                .filter(rv -> rv.getId().equals(updateReviewForm.getReviewId()))
                .findFirst().orElseThrow(() -> new CustomException(ExceptionCode.REVIEW_NOT_FOUND));

        if (matchedReview.getSlotDateTime().plusWeeks(1).isBefore(LocalDateTime.now())) {
            throw new CustomException(ExceptionCode.OLD_COMPLETED_RESERVATION);
        }

        // 리뷰 및 별점 정정
        double oldStarRating = matchedReview.getStarRating();
        Store matchedStore = storeRepository.findById(matchedReview.getStoreId())
                .orElseThrow(() -> new CustomException(ExceptionCode.STORE_NOT_FOUND));

        matchedReview.setReview(updateReviewForm.getReview());
        matchedReview.setStarRating(updateReviewForm.getStarRating());
        matchedReview.setUpdatedAt(LocalDateTime.now());

        matchedStore.removeRating(oldStarRating);
        matchedStore.addRating(updateReviewForm.getStarRating());

        return matchedReview;
    }

    @Transactional
    public void deleteReview(String token, Long reviewId) {

        // 고객 정보 확인
        CustomerDto customerDto = userClient.getInfo(token).getBody();
        if (customerDto == null) {
            throw new CustomException(ExceptionCode.CUSTOMER_NOT_FOUND);
        }

        // 수정 가능 여부 확인
        Review matchedReview = reviewRepository.findAllByCustomerId(customerDto.getId()).stream()
                .filter(rv -> rv.getId().equals(reviewId)
                        && rv.getSlotDateTime().plusWeeks(1).isAfter(LocalDateTime.now()))
                .findFirst().orElseThrow(() -> new CustomException(ExceptionCode.OLD_COMPLETED_RESERVATION));

        // 별점 정정
        double oldStarRating = matchedReview.getStarRating();
        Store matchedStore = storeRepository.findById(matchedReview.getStoreId())
                .orElseThrow(() -> new CustomException(ExceptionCode.STORE_NOT_FOUND));

        matchedStore.removeRating(oldStarRating);
        reviewRepository.delete(matchedReview);
    }
}
