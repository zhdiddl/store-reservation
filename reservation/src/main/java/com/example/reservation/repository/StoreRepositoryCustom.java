package com.example.reservation.repository;

import com.example.reservation.domain.Store;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> searchByName(String name);
}
