package com.example.reservation.service;

import com.example.reservation.domain.Store;
import com.example.reservation.domain.StoreSlot;
import com.example.reservation.dto.StoreDto;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ExceptionCode;
import com.example.reservation.form.AddStoreForm;
import com.example.reservation.form.UpdateStoreForm;
import com.example.reservation.form.UpdateStoreSlotForm;
import com.example.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<StoreDto> getStore(Long partnerId) {
        List<Store> stores = storeRepository.findByPartnerId(partnerId)
                .orElseThrow(() -> new CustomException(ExceptionCode.STORE_NOT_FOUND));

        return stores.stream().map(StoreDto::from).collect(Collectors.toList());
    }

    @Transactional
    public Store addStore(Long partnerId, AddStoreForm addStoreForm) {
        if (addStoreForm.getName() == null || addStoreForm.getName().isBlank()) {
            throw new CustomException(ExceptionCode.STORE_NAME_BLANK);
        } else if (storeRepository.findByName(addStoreForm.getName()).isPresent()) {
            throw new CustomException(ExceptionCode.STORE_NAME_ALREADY_EXISTS);
        }
        return storeRepository.save(Store.of(partnerId, addStoreForm));
    }

    @Transactional
    public Store updateStore(Long partnerId, UpdateStoreForm updateStoreForm) {
        Store matchedStore = storeRepository.findByIdAndPartnerId(updateStoreForm.getId(), partnerId)
                .orElseThrow(() -> new CustomException(ExceptionCode.STORE_NOT_FOUND));
        log.debug("수정할 가게: {}", matchedStore.getName());
        matchedStore.setName(updateStoreForm.getName());
        matchedStore.setAddress(updateStoreForm.getAddress());
        matchedStore.setPhone(updateStoreForm.getPhone());
        matchedStore.setDescription(updateStoreForm.getDescription());

        if (updateStoreForm.getSlots() != null) {
            for (UpdateStoreSlotForm slotForm : updateStoreForm.getSlots()) {
                StoreSlot matchedSlot = matchedStore.getSlots().stream()
                        .filter(slot -> slot.getId().equals(slotForm.getId()))
                        .findFirst().orElseThrow(() -> new CustomException(ExceptionCode.SLOT_NOT_FOUND));
                matchedSlot.setDateTime(slotForm.getDateTime());
                matchedSlot.setQuantity(slotForm.getQuantity());
            }
        }
        return storeRepository.save(matchedStore);
    }

    public void deleteStore(Long partnerId, Long storeId) {
        Store store = storeRepository.findByIdAndPartnerId(storeId, partnerId)
                .orElseThrow(() -> new CustomException(ExceptionCode.STORE_NOT_FOUND));
        log.debug("삭제할 가게: {}", store.getName());
        storeRepository.delete(store);
    }
}
