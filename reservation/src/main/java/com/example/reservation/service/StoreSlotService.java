package com.example.reservation.service;

import com.example.reservation.domain.Store;
import com.example.reservation.domain.StoreSlot;
import com.example.reservation.dto.StoreSlotDto;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ExceptionCode;
import com.example.reservation.form.AddStoreSlotForm;
import com.example.reservation.form.UpdateStoreSlotForm;
import com.example.reservation.repository.StoreRepository;
import com.example.reservation.repository.StoreSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class StoreSlotService {
    private final StoreRepository storeRepository;
    private final StoreSlotRepository storeSlotRepository;

    @Transactional(readOnly = true)
    public List<StoreSlotDto> getStoreSlot(Long storeId) {
        List<StoreSlot> storeSlots = storeSlotRepository.findByStoreId(storeId)
                .orElseThrow(() -> new CustomException(ExceptionCode.SLOT_NOT_FOUND));

        return storeSlots.stream().map(StoreSlotDto::from).collect(Collectors.toList());
    }

    @Transactional
    public StoreSlot addStoreSlot(Long partnerId, AddStoreSlotForm addStoreSlotForm) {
        // 해당하는 가게 유무 확인
        Store matchedStore = storeRepository.findByIdAndPartnerId(addStoreSlotForm.getStoreId(), partnerId)
                .orElseThrow(() -> new CustomException(ExceptionCode.STORE_NOT_FOUND));

        // 슬롯 시간이 유효한지 확인
        if (addStoreSlotForm.getDateTime().isBefore(LocalDateTime.now())) {
            throw new CustomException(ExceptionCode.SLOT_NOT_ALLOWED_TO_ADD);
        }

        // 슬롯 중복 여부 확인
        if (matchedStore.getSlots().stream()
                .anyMatch(slot -> slot.getDateTime().equals(addStoreSlotForm.getDateTime()))) {
            throw new CustomException(ExceptionCode.SLOT_ALREADY_EXISTS);
        }

        // 슬롯 저장
        StoreSlot storeSlot = StoreSlot.of(partnerId, addStoreSlotForm);
        storeSlot.setStore(matchedStore);

        matchedStore.getSlots().add(storeSlot);

        return storeSlotRepository.save(storeSlot);
    }

    @Transactional
    public StoreSlot updateStoreSlot(Long partnerId, UpdateStoreSlotForm updateStoreSlotForm) {
        StoreSlot storeSlot = storeSlotRepository.findById(updateStoreSlotForm.getId())
                .filter(slot -> slot.getPartnerId().equals(partnerId))
                .orElseThrow(() -> new CustomException(ExceptionCode.SLOT_NOT_FOUND));
        log.debug("수정할 슬롯: {}", storeSlot.getDateTime());

        storeSlot.setDateTime(updateStoreSlotForm.getDateTime());
        storeSlot.setQuantity(updateStoreSlotForm.getQuantity());
        return storeSlotRepository.save(storeSlot);
    }

    @Transactional
    public void deleteStoreSlot(Long partnerId, Long storeSlotId) {
        StoreSlot storeSlot = storeSlotRepository.findById(storeSlotId)
                .filter(slot -> slot.getPartnerId().equals(partnerId))
                .orElseThrow(() -> new CustomException(ExceptionCode.SLOT_NOT_FOUND));
        log.debug("삭제할 슬롯: {}", storeSlot.getDateTime());

        storeSlotRepository.delete(storeSlot);
    }
}
