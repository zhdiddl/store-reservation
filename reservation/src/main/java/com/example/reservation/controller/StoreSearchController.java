package com.example.reservation.controller;

import com.example.reservation.domain.Store;
import com.example.reservation.service.StoreSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/search")
public class StoreSearchController {
    private final StoreSearchService storeSearchService;

    @GetMapping("/name")
    public ResponseEntity<List<Store>> searchStore(@RequestParam String name) {
        return ResponseEntity.ok(storeSearchService.searchByName(name));
    }

    @GetMapping("/rating-desc")
    public ResponseEntity<Page<Store>> getByStarRating(
            @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(storeSearchService.getStoresByStarRatingDesc(page, size));
    }

    @GetMapping("/name-asc")
    public ResponseEntity<Page<Store>> getByName(
            @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(storeSearchService.getStoresByNameAsc(page, size));
    }
}
