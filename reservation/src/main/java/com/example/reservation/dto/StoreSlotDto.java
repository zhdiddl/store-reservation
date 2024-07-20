package com.example.reservation.dto;

import com.example.reservation.domain.StoreSlot;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSlotDto {
    private Long id;
    private LocalDateTime dateTime;
    private Integer quantity;

    public static StoreSlotDto from(StoreSlot storeSlot) {
        return StoreSlotDto.builder()
                .id(storeSlot.getId())
                .dateTime(storeSlot.getDateTime())
                .quantity(storeSlot.getQuantity())
                .build();
    }
}
