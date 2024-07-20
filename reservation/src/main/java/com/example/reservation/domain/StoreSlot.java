package com.example.reservation.domain;

import com.example.domain.domain.BaseEntity;
import com.example.reservation.form.AddStoreSlotForm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StoreSlot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long partnerId;

    @Audited
    @Column(nullable = false)
    private LocalDateTime dateTime;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference
    private Store store;

    @OneToMany
    @JoinColumn(name = "slot_id")
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();

    public static StoreSlot of(Long partnerId, AddStoreSlotForm addStoreSlotForm) {
        return StoreSlot.builder()
                .partnerId(partnerId)
                .dateTime(addStoreSlotForm.getDateTime())
                .quantity(addStoreSlotForm.getQuantity())
                .build();
    }
}
