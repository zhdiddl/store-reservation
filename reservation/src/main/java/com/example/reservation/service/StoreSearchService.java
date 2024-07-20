package com.example.reservation.service;

import com.example.reservation.domain.Store;
import com.example.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreSearchService {
    private final StoreRepository storeRepository;

    public List<Store> searchByName(String name) {
        return storeRepository.searchByName(name);
    }

    public Page<Store> getStoresByStarRatingDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return storeRepository.findByStarRatingDesc(pageable);
    }

    public Page<Store> getStoresByNameAsc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return storeRepository.findByNameAsc(pageable);
    }
}
