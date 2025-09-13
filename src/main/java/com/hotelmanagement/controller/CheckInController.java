package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.CheckIn;
import com.hotelmanagement.service.CheckInService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/checkins")
@CrossOrigin(origins = "*")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CheckIn>>> getAllCheckIns() {
        List<CheckIn> checkIns = checkInService.getAllCheckIns();
        return ResponseEntity.ok(ApiResponse.success("Check-ins retrieved successfully", checkIns));
    }

    @GetMapping("/{folioNo}")
    public ResponseEntity<ApiResponse<CheckIn>> getCheckInById(@PathVariable String folioNo) {
        CheckIn checkIn = checkInService.getCheckInById(folioNo);
        return ResponseEntity.ok(ApiResponse.success("Check-in retrieved successfully", checkIn));
    }

    @GetMapping("/search/guest")
    public ResponseEntity<ApiResponse<List<CheckIn>>> getCheckInsByGuestName(@RequestParam String guestName) {
        List<CheckIn> checkIns = checkInService.getCheckInsByGuestName(guestName);
        return ResponseEntity.ok(ApiResponse.success("Check-ins found", checkIns));
    }

    @GetMapping("/inhouse")
    public ResponseEntity<ApiResponse<List<CheckIn>>> getInHouseGuests() {
        List<CheckIn> inHouseGuests = checkInService.getInHouseGuests();
        return ResponseEntity.ok(ApiResponse.success("In-house guests retrieved", inHouseGuests));
    }

    @GetMapping("/checkouts/{date}")
    public ResponseEntity<ApiResponse<List<CheckIn>>> getCheckOutsForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<CheckIn> checkOuts = checkInService.getCheckOutsForDate(date);
        return ResponseEntity.ok(ApiResponse.success("Check-outs for date retrieved", checkOuts));
    }

    @GetMapping("/walkins")
    public ResponseEntity<ApiResponse<List<CheckIn>>> getWalkInGuests() {
        List<CheckIn> walkIns = checkInService.getWalkInGuests();
        return ResponseEntity.ok(ApiResponse.success("Walk-in guests retrieved", walkIns));
    }

    @PostMapping("/with-reservation")
    public ResponseEntity<ApiResponse<CheckIn>> checkInWithReservation(
            @RequestParam String reservationNo, @RequestParam String roomId) {
        CheckIn checkIn = checkInService.checkInWithReservation(reservationNo, roomId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Guest checked in successfully", checkIn));
    }

    @PostMapping("/walkin")
    public ResponseEntity<ApiResponse<CheckIn>> walkInCheckIn(
            @Valid @RequestBody CheckIn checkInData, @RequestParam String roomId) {
        CheckIn checkIn = checkInService.walkInCheckIn(checkInData, roomId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Walk-in guest checked in successfully", checkIn));
    }

    @PutMapping("/{folioNo}")
    public ResponseEntity<ApiResponse<CheckIn>> updateCheckIn(
            @PathVariable String folioNo, @Valid @RequestBody CheckIn checkIn) {
        CheckIn updatedCheckIn = checkInService.updateCheckIn(folioNo, checkIn);
        return ResponseEntity.ok(ApiResponse.success("Check-in updated successfully", updatedCheckIn));
    }

    @GetMapping("/count/inhouse")
    public ResponseEntity<ApiResponse<Long>> getInHouseGuestCount() {
        long count = checkInService.getInHouseGuestCount();
        return ResponseEntity.ok(ApiResponse.success("In-house guest count retrieved", count));
    }
}
