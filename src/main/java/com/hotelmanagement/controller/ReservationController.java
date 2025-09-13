package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.Reservation;
import com.hotelmanagement.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Reservation>>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(ApiResponse.success("Reservations retrieved successfully", reservations));
    }

    @GetMapping("/{reservationNo}")
    public ResponseEntity<ApiResponse<Reservation>> getReservationById(@PathVariable String reservationNo) {
        Reservation reservation = reservationService.getReservationById(reservationNo);
        return ResponseEntity.ok(ApiResponse.success("Reservation retrieved successfully", reservation));
    }

    @GetMapping("/search/guest")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByGuestName(@RequestParam String guestName) {
        List<Reservation> reservations = reservationService.getReservationsByGuestName(guestName);
        return ResponseEntity.ok(ApiResponse.success("Reservations found", reservations));
    }

    @GetMapping("/search/mobile")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByMobileNumber(@RequestParam String mobileNumber) {
        List<Reservation> reservations = reservationService.getReservationsByMobileNumber(mobileNumber);
        return ResponseEntity.ok(ApiResponse.success("Reservations found", reservations));
    }

    @GetMapping("/arrivals/{date}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getExpectedArrivals(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Reservation> arrivals = reservationService.getExpectedArrivals(date);
        return ResponseEntity.ok(ApiResponse.success("Expected arrivals retrieved", arrivals));
    }

    @GetMapping("/departures/{date}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getExpectedDepartures(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Reservation> departures = reservationService.getExpectedDepartures(date);
        return ResponseEntity.ok(ApiResponse.success("Expected departures retrieved", departures));
    }

    @GetMapping("/pending-checkins")
    public ResponseEntity<ApiResponse<List<Reservation>>> getPendingCheckIns() {
        List<Reservation> pendingCheckIns = reservationService.getPendingCheckIns();
        return ResponseEntity.ok(ApiResponse.success("Pending check-ins retrieved", pendingCheckIns));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Reservation>> createReservation(@Valid @RequestBody Reservation reservation) {
        Reservation createdReservation = reservationService.createReservation(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Reservation created successfully", createdReservation));
    }

    @PutMapping("/{reservationNo}")
    public ResponseEntity<ApiResponse<Reservation>> updateReservation(
            @PathVariable String reservationNo, @Valid @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(reservationNo, reservation);
        return ResponseEntity.ok(ApiResponse.success("Reservation updated successfully", updatedReservation));
    }

    @DeleteMapping("/{reservationNo}")
    public ResponseEntity<ApiResponse<Void>> deleteReservation(@PathVariable String reservationNo) {
        reservationService.deleteReservation(reservationNo);
        return ResponseEntity.ok(ApiResponse.success("Reservation deleted successfully"));
    }

    @PostMapping("/{reservationNo}/checkin-room")
    public ResponseEntity<ApiResponse<Void>> incrementRoomsCheckedIn(@PathVariable String reservationNo) {
        reservationService.incrementRoomsCheckedIn(reservationNo);
        return ResponseEntity.ok(ApiResponse.success("Room checked in successfully"));
    }

    @GetMapping("/{reservationNo}/can-checkin")
    public ResponseEntity<ApiResponse<Boolean>> canCheckInMoreRooms(@PathVariable String reservationNo) {
        boolean canCheckIn = reservationService.canCheckInMoreRooms(reservationNo);
        return ResponseEntity.ok(ApiResponse.success("Check-in availability checked", canCheckIn));
    }
}
