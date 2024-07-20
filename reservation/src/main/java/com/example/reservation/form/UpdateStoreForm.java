package com.example.reservation.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreForm {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private List<UpdateStoreSlotForm> slots;
}
