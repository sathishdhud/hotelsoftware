package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.PostTransaction;
import com.hotelmanagement.service.PostTransactionService;
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
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class PostTransactionController {

    @Autowired
    private PostTransactionService postTransactionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostTransaction>>> getAllTransactions() {
        List<PostTransaction> transactions = postTransactionService.getAllTransactions();
        return ResponseEntity.ok(ApiResponse.success("Transactions retrieved successfully", transactions));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<PostTransaction>> getTransactionById(@PathVariable String transactionId) {
        PostTransaction transaction = postTransactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(ApiResponse.success("Transaction retrieved successfully", transaction));
    }

    @GetMapping("/by-folio/{folioNo}")
    public ResponseEntity<ApiResponse<List<PostTransaction>>> getTransactionsByFolioNo(@PathVariable String folioNo) {
        List<PostTransaction> transactions = postTransactionService.getTransactionsByFolioNo(folioNo);
        return ResponseEntity.ok(ApiResponse.success("Transactions retrieved by folio number", transactions));
    }

    @GetMapping("/by-bill/{billNo}")
    public ResponseEntity<ApiResponse<List<PostTransaction>>> getTransactionsByBillNo(@PathVariable String billNo) {
        List<PostTransaction> transactions = postTransactionService.getTransactionsByBillNo(billNo);
        return ResponseEntity.ok(ApiResponse.success("Transactions retrieved by bill number", transactions));
    }

    @GetMapping("/by-room/{roomId}")
    public ResponseEntity<ApiResponse<List<PostTransaction>>> getTransactionsByRoomId(@PathVariable String roomId) {
        List<PostTransaction> transactions = postTransactionService.getTransactionsByRoomId(roomId);
        return ResponseEntity.ok(ApiResponse.success("Transactions retrieved by room ID", transactions));
    }

    @GetMapping("/total/by-folio/{folioNo}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalTransactionsByFolioNo(@PathVariable String folioNo) {
        BigDecimal total = postTransactionService.getTotalTransactionsByFolioNo(folioNo);
        return ResponseEntity.ok(ApiResponse.success("Total transactions calculated", total));
    }

    @GetMapping("/total/by-bill/{billNo}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalTransactionsByBillNo(@PathVariable String billNo) {
        BigDecimal total = postTransactionService.getTotalTransactionsByBillNo(billNo);
        return ResponseEntity.ok(ApiResponse.success("Total transactions calculated", total));
    }

    @PostMapping("/inhouse")
    public ResponseEntity<ApiResponse<PostTransaction>> createInHouseTransaction(@Valid @RequestBody PostTransaction transaction) {
        PostTransaction createdTransaction = postTransactionService.createInHouseTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("In-house transaction created", createdTransaction));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<PostTransaction>> createCheckoutTransaction(@Valid @RequestBody PostTransaction transaction) {
        PostTransaction createdTransaction = postTransactionService.createCheckoutTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Checkout transaction created", createdTransaction));
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<PostTransaction>> updateTransaction(
            @PathVariable String transactionId, @Valid @RequestBody PostTransaction transaction) {
        PostTransaction updatedTransaction = postTransactionService.updateTransaction(transactionId, transaction);
        return ResponseEntity.ok(ApiResponse.success("Transaction updated successfully", updatedTransaction));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable String transactionId) {
        postTransactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok(ApiResponse.success("Transaction deleted successfully"));
    }

    @GetMapping("/by-audit-date/{auditDate}")
    public ResponseEntity<ApiResponse<List<PostTransaction>>> getTransactionsByAuditDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate auditDate) {
        List<PostTransaction> transactions = postTransactionService.getTransactionsByAuditDate(auditDate);
        return ResponseEntity.ok(ApiResponse.success("Transactions retrieved by audit date", transactions));
    }

    @PostMapping("/post-room-charges/{auditDate}")
    public ResponseEntity<ApiResponse<Void>> postRoomChargesForAuditDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate auditDate) {
        postTransactionService.postRoomChargesForAuditDate(auditDate);
        return ResponseEntity.ok(ApiResponse.success("Room charges posted for audit date"));
    }
}
