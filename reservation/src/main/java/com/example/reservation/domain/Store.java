package com.example.reservation.domain;


import com.example.domain.domain.BaseEntity;
import com.example.reservation.form.AddStoreForm;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long partnerId;

    @Column(unique = true, nullable = false)
    private String name;

    private String address;
    private String phone;
    private String description;

    private double starRating;
    private int ratingCount;
    private double totalRating;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    @JsonManagedReference
    private List<StoreSlot> slots = new ArrayList<>();

    public static Store of(Long partnerId, AddStoreForm addStoreForm) {
        return Store.builder()
                .partnerId(partnerId)
                .name(addStoreForm.getName())
                .address(addStoreForm.getAddress())
                .phone(addStoreForm.getPhone())
                .description(addStoreForm.getDescription())
                .slots(addStoreForm.getSlots().stream()
                        .map(addStoreSlotForm -> StoreSlot.of(partnerId, addStoreSlotForm))
                        .collect(Collectors.toList()))
                .build();
    }

    public void addRating(double newRating) {
        this.totalRating += newRating;
        this.ratingCount++;
        this.starRating = this.totalRating / this.ratingCount;
    }

    public void removeRating(double oldStarRating) {
        if (ratingCount > 0) {
            this.totalRating -= oldStarRating;
            this.ratingCount--;
            if (this.ratingCount > 0) {
                this.starRating = this.totalRating / this.ratingCount;
            } else {
                this.starRating = 0;
                this.totalRating = 0;
            }
        } else {
            throw new IllegalStateException("rating count is zero");
        }
    }
}
