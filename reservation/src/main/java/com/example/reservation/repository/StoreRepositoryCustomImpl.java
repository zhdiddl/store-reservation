package com.example.reservation.repository;

import com.example.reservation.domain.QStore;
import com.example.reservation.domain.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Store> searchByName(String name) {
        String searchKeyword = "%" + name + "%";

        QStore store = QStore.store;
        return queryFactory.selectFrom(store)
                .where(store.name.like(searchKeyword)).fetch();
    }
}
