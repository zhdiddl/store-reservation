package com.example.reservation.repository;

import com.example.reservation.domain.StoreSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreSlotRepository extends JpaRepository<StoreSlot, Long> {
    Optional<List<StoreSlot>> findByStoreId(Long storeId);
}
