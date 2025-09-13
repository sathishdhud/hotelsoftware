package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.FoBill;
import com.hotelmanagement.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/billing")
@CrossOrigin(origins = "*")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping("/bills")
    public ResponseEntity<ApiResponse<List<FoBill>>> getAllBills() {
        List<FoBill> bills = billingService.getAllBills();
        return ResponseEntity.ok(ApiResponse.success("Bills retrieved successfully", bills));
    }

    @GetMapping("/bills/{billNo}")
    public ResponseEntity<ApiResponse<FoBill>> getBillById(@PathVariable String billNo) {
        FoBill bill = billingService.getBillById(billNo);
        return ResponseEntity.ok(ApiResponse.success("Bill retrieved successfully", bill));
    }

    @GetMapping("/bills/by-folio/{folioNo}")
    public ResponseEntity<ApiResponse<FoBill>> getBillByFolioNo(@PathVariable String folioNo) {
        FoBill bill = billingService.getBillByFolioNo(folioNo);
        return ResponseEntity.ok(ApiResponse.success("Bill retrieved by folio number", bill));
    }

    @GetMapping("/bills/search/guest")
    public ResponseEntity<ApiResponse<List<FoBill>>> getBillsByGuestName(@RequestParam String guestName) {
        List<FoBill> bills = billingService.getBillsByGuestName(guestName);
        return ResponseEntity.ok(ApiResponse.success("Bills found by guest name", bills));
    }

    @PostMapping("/generate/{folioNo}")
    public ResponseEntity<ApiResponse<FoBill>> generateBill(@PathVariable String folioNo) {
        FoBill bill = billingService.generateBill(folioNo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Bill generated successfully", bill));
    }

    @PostMapping("/split/{originalBillNo}")
    public ResponseEntity<ApiResponse<FoBill>> createSplitBill(
            @PathVariable String originalBillNo, @RequestBody List<String> transactionIds) {
        FoBill splitBill = billingService.createSplitBill(originalBillNo, transactionIds);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Split bill created successfully", splitBill));
    }

    @GetMapping("/total/by-date/{date}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalBillAmountForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        BigDecimal total = billingService.getTotalBillAmountForDate(date);
        return ResponseEntity.ok(ApiResponse.success("Total bill amount calculated", total));
    }

    @GetMapping("/count/by-date/{date}")
    public ResponseEntity<ApiResponse<Long>> getBillCountForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        long count = billingService.getBillCountForDate(date);
        return ResponseEntity.ok(ApiResponse.success("Bill count retrieved", count));
    }

    @DeleteMapping("/bills/{billNo}")
    public ResponseEntity<ApiResponse<Void>> deleteBill(@PathVariable String billNo) {
        billingService.deleteBill(billNo);
        return ResponseEntity.ok(ApiResponse.success("Bill deleted successfully"));
    }
}
