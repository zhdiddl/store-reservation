package com.example.reservation.controller;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.user.UserVo;
import com.example.reservation.dto.StoreDto;
import com.example.reservation.dto.StoreSlotDto;
import com.example.reservation.form.AddStoreForm;
import com.example.reservation.form.AddStoreSlotForm;
import com.example.reservation.form.UpdateStoreForm;
import com.example.reservation.form.UpdateStoreSlotForm;
import com.example.reservation.service.StoreService;
import com.example.reservation.service.StoreSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/partner/store")
public class PartnerStoreController {
    private final StoreService storeService;
    private final StoreSlotService storeSlotService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @GetMapping("/{partnerId}")
    public ResponseEntity<List<StoreDto>> getStoreByPartnerId(@PathVariable Long partnerId) {
        return ResponseEntity.ok(storeService.getStore(partnerId));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<List<StoreSlotDto>> getStoreSlotByStoreId(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeSlotService.getStoreSlot(storeId));
    }

    @PostMapping("/add")
    public ResponseEntity<StoreDto> addStore(@RequestHeader("X-AUTH-TOKEN") String token,
                                             @RequestBody AddStoreForm addStoreForm) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        return ResponseEntity.ok(StoreDto.from(storeService.addStore(userVo.getId(), addStoreForm)));
    }

    @PostMapping("/add/slot")
    public ResponseEntity<StoreSlotDto> addStoreSlot(@RequestHeader("X-AUTH-TOKEN") String token,
                                                     @RequestBody AddStoreSlotForm addStoreSlotForm) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        return ResponseEntity.ok(StoreSlotDto.from(storeSlotService.addStoreSlot(userVo.getId(), addStoreSlotForm)));
    }

    @PutMapping("/update")
    public ResponseEntity<StoreDto> updateStore(@RequestHeader("X-AUTH-TOKEN") String token,
                                                @RequestBody UpdateStoreForm updateStoreForm) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        return ResponseEntity.ok(StoreDto.from(storeService.updateStore(userVo.getId(), updateStoreForm)));
    }

    @PutMapping("/update/slot")
    public ResponseEntity<StoreSlotDto> updateStoreSlot(@RequestHeader("X-AUTH-TOKEN") String token,
                                                        @RequestBody UpdateStoreSlotForm updateStoreSlotForm) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        return ResponseEntity.ok(StoreSlotDto.from(storeSlotService.updateStoreSlot(userVo.getId(), updateStoreSlotForm)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStore(@RequestHeader("X-AUTH-TOKEN") String token,
                                              @RequestParam Long storeId) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        storeService.deleteStore(userVo.getId(), storeId);
        return ResponseEntity.ok("요청한 가게가 삭제되었습니다.");
    }

    @DeleteMapping("/delete/slot")
    public ResponseEntity<String> deleteStoreSlot(@RequestHeader("X-AUTH-TOKEN") String token,
                                                  @RequestParam Long slotId) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        storeSlotService.deleteStoreSlot(userVo.getId(), slotId);
        return ResponseEntity.ok("요청한 슬롯이 삭제되었습니다.");
    }
}
