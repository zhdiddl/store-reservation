package com.example.reservation.repository;

import com.example.reservation.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCustomerId(Long customerId);

    Optional<Review> findByReservationId(Long reservationId);

    @Query("SELECT r FROM Review r WHERE r.storeId = :storeId ORDER BY r.updatedAt DESC")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
