package com.example.reservation.domain;

import com.example.reservation.form.AddReservationForm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    @JsonBackReference
    private StoreSlot storeSlot;

    private Long storeId;
    private Long customerId;
    private String customerPhone;
    private Integer visitorCount;

    @Audited
    private LocalDateTime slotDateTime;

    private boolean visitCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public static Reservation of(Long customerId, AddReservationForm addReservationForm, StoreSlot storeSlot) {
        return Reservation.builder()
                .storeSlot(storeSlot)
                .storeId(storeSlot.getStore().getId())
                .customerId(customerId)
                .customerPhone(addReservationForm.getCustomerPhone())
                .visitorCount(addReservationForm.getVisitorCount())
                .slotDateTime(addReservationForm.getSlotDateTime())
                .visitCompleted(false)
                .createdAt(LocalDateTime.now())
                .expiredAt(addReservationForm.getSlotDateTime().minusMinutes(10))
                .build();
    }
}
