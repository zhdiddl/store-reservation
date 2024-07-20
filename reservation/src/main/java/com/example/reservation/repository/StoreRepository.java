package com.example.reservation.repository;

import com.example.reservation.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
    @EntityGraph(attributePaths = {"slots"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Store> findByIdAndPartnerId(Long id, Long partnerId);

    @EntityGraph(attributePaths = {"slots"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<List<Store>> findByPartnerId(Long partnerId);

    @EntityGraph(attributePaths = {"slots"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Store> findByName(String name);

    @Query("SELECT s from Store s ORDER BY s.starRating DESC")
    Page<Store> findByStarRatingDesc(Pageable pageable);

    @Query("SELECT s from Store s ORDER BY s.name ASC")
    Page<Store> findByNameAsc(Pageable pageable);
}
