package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.Shift;
import com.hotelmanagement.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shifts")
@CrossOrigin(origins = "*")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Shift>>> getAllShifts() {
        List<Shift> shifts = shiftService.getAllShifts();
        return ResponseEntity.ok(ApiResponse.success("Shifts retrieved successfully", shifts));
    }

    @GetMapping("/{shiftNo}")
    public ResponseEntity<ApiResponse<Shift>> getShiftById(@PathVariable String shiftNo) {
        Shift shift = shiftService.getShiftById(shiftNo);
        return ResponseEntity.ok(ApiResponse.success("Shift retrieved successfully", shift));
    }

    @GetMapping("/by-date/{shiftDate}")
    public ResponseEntity<ApiResponse<List<Shift>>> getShiftsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate shiftDate) {
        List<Shift> shifts = shiftService.getShiftsByDate(shiftDate);
        return ResponseEntity.ok(ApiResponse.success("Shifts retrieved by date", shifts));
    }

    @PostMapping("/create-or-update")
    public ResponseEntity<ApiResponse<Shift>> createOrUpdateShift(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate shiftDate,
            @RequestParam String shiftNo,
            @RequestParam BigDecimal balance) {
        Shift shift = shiftService.createOrUpdateShift(shiftDate, shiftNo, balance);
        return ResponseEntity.ok(ApiResponse.success("Shift created/updated successfully", shift));
    }

    @GetMapping("/current-shift-no")
    public ResponseEntity<ApiResponse<String>> getCurrentShiftNo() {
        String currentShiftNo = shiftService.getCurrentShiftNo();
        return ResponseEntity.ok(ApiResponse.success("Current shift number retrieved", currentShiftNo));
    }

    @PostMapping("/process-shift-change")
    public ResponseEntity<ApiResponse<Void>> processShiftChange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate shiftDate,
            @RequestParam String shiftNo,
            @RequestParam BigDecimal balance) {
        shiftService.processShiftChange(shiftDate, shiftNo, balance);
        return ResponseEntity.ok(ApiResponse.success("Shift change processed successfully"));
    }
}
