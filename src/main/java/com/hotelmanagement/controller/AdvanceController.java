package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.Advance;
import com.hotelmanagement.service.AdvanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/advances")
@CrossOrigin(origins = "*")
public class AdvanceController {

    @Autowired
    private AdvanceService advanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Advance>>> getAllAdvances() {
        List<Advance> advances = advanceService.getAllAdvances();
        return ResponseEntity.ok(ApiResponse.success("Advances retrieved successfully", advances));
    }

    @GetMapping("/{receiptNo}")
    public ResponseEntity<ApiResponse<Advance>> getAdvanceById(@PathVariable String receiptNo) {
        Advance advance = advanceService.getAdvanceById(receiptNo);
        return ResponseEntity.ok(ApiResponse.success("Advance retrieved successfully", advance));
    }

    @GetMapping("/by-folio/{folioNo}")
    public ResponseEntity<ApiResponse<List<Advance>>> getAdvancesByFolioNo(@PathVariable String folioNo) {
        List<Advance> advances = advanceService.getAdvancesByFolioNo(folioNo);
        return ResponseEntity.ok(ApiResponse.success("Advances retrieved by folio number", advances));
    }

    @GetMapping("/by-reservation/{reservationNo}")
    public ResponseEntity<ApiResponse<List<Advance>>> getAdvancesByReservationNo(@PathVariable String reservationNo) {
        List<Advance> advances = advanceService.getAdvancesByReservationNo(reservationNo);
        return ResponseEntity.ok(ApiResponse.success("Advances retrieved by reservation number", advances));
    }

    @GetMapping("/by-bill/{billNo}")
    public ResponseEntity<ApiResponse<List<Advance>>> getAdvancesByBillNo(@PathVariable String billNo) {
        List<Advance> advances = advanceService.getAdvancesByBillNo(billNo);
        return ResponseEntity.ok(ApiResponse.success("Advances retrieved by bill number", advances));
    }

    @GetMapping("/total/by-folio/{folioNo}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalAdvancesByFolioNo(@PathVariable String folioNo) {
        BigDecimal total = advanceService.getTotalAdvancesByFolioNo(folioNo);
        return ResponseEntity.ok(ApiResponse.success("Total advances calculated", total));
    }

    @GetMapping("/total/by-reservation/{reservationNo}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalAdvancesByReservationNo(@PathVariable String reservationNo) {
        BigDecimal total = advanceService.getTotalAdvancesByReservationNo(reservationNo);
        return ResponseEntity.ok(ApiResponse.success("Total advances calculated", total));
    }

    @GetMapping("/total/by-bill/{billNo}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalAdvancesByBillNo(@PathVariable String billNo) {
        BigDecimal total = advanceService.getTotalAdvancesByBillNo(billNo);
        return ResponseEntity.ok(ApiResponse.success("Total advances calculated", total));
    }

    @PostMapping("/reservation")
    public ResponseEntity<ApiResponse<Advance>> createAdvanceForReservation(@Valid @RequestBody Advance advance) {
        Advance createdAdvance = advanceService.createAdvanceForReservation(advance);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Advance created for reservation", createdAdvance));
    }

    @PostMapping("/inhouse")
    public ResponseEntity<ApiResponse<Advance>> createAdvanceForInHouseGuest(@Valid @RequestBody Advance advance) {
        Advance createdAdvance = advanceService.createAdvanceForInHouseGuest(advance);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Advance created for in-house guest", createdAdvance));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<Advance>> createAdvanceForCheckedOutGuest(@Valid @RequestBody Advance advance) {
        Advance createdAdvance = advanceService.createAdvanceForCheckedOutGuest(advance);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Advance created for checked-out guest", createdAdvance));
    }

    @PutMapping("/{receiptNo}")
    public ResponseEntity<ApiResponse<Advance>> updateAdvance(
            @PathVariable String receiptNo, @Valid @RequestBody Advance advance) {
        Advance updatedAdvance = advanceService.updateAdvance(receiptNo, advance);
        return ResponseEntity.ok(ApiResponse.success("Advance updated successfully", updatedAdvance));
    }

    @DeleteMapping("/{receiptNo}")
    public ResponseEntity<ApiResponse<Void>> deleteAdvance(@PathVariable String receiptNo) {
        advanceService.deleteAdvance(receiptNo);
        return ResponseEntity.ok(ApiResponse.success("Advance deleted successfully"));
    }

    @GetMapping("/total/by-date/{date}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalAdvancesForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        BigDecimal total = advanceService.getTotalAdvancesForDate(date);
        return ResponseEntity.ok(ApiResponse.success("Total advances for date calculated", total));
    }
}
