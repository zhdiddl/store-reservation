package com.example.reservation.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreSlotForm {

    private Long id;
    private LocalDateTime dateTime;
    private Integer quantity;
}
