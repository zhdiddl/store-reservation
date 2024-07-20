package com.example.reservation.dto;

import com.example.reservation.domain.Store;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private List<StoreSlotDto> slots;

    public static StoreDto from(Store store) {
        List<StoreSlotDto> slotDtos = store.getSlots().stream()
                .map(StoreSlotDto::from)
                .toList();

        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phone(store.getPhone())
                .description(store.getDescription())
                .slots(slotDtos)
                .build();
    }
}
