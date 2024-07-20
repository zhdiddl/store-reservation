package com.example.reservation.controller;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.user.UserVo;
import com.example.reservation.dto.ReservationDto;
import com.example.reservation.form.AddReservationForm;
import com.example.reservation.service.KioskService;
import com.example.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final KioskService kioskService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @GetMapping("/get")
    public ResponseEntity<ReservationDto> getReservation(
            @RequestHeader("X-AUTH-TOKEN") String token,
            @RequestParam Long reservationId) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        return ResponseEntity.ok(ReservationDto.from(reservationService.getReservation(reservationId, userVo.getId())));
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationDto> createReservation(
            @RequestHeader("X-AUTH-TOKEN") String token,
            @RequestBody AddReservationForm addReservationForm) {
        return ResponseEntity.ok(ReservationDto.from(reservationService.createReservation(token, addReservationForm)));
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelReservation(
            @RequestHeader("X-AUTH-TOKEN") String token,
            @RequestParam Long reservationId) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        reservationService.cancelReservation(reservationId, userVo.getId());
        return ResponseEntity.ok("요청한 예약이 삭제되었습니다.");
    }

    @PutMapping("/complete")
    public ResponseEntity<String> completeVisit(
            @RequestParam String phone, @RequestParam Long slotId) {
        kioskService.completeVisit(phone, slotId);
        return ResponseEntity.ok("방문이 확인되었습니다.");
    }
}
