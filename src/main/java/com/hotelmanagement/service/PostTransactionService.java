package com.hotelmanagement.service;

import com.hotelmanagement.entity.PostTransaction;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.PostTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PostTransactionService {

    @Autowired
    private PostTransactionRepository postTransactionRepository;

    public List<PostTransaction> getAllTransactions() {
        return postTransactionRepository.findAll();
    }

    public PostTransaction getTransactionById(String transactionId) {
        return postTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + transactionId));
    }

    public List<PostTransaction> getTransactionsByFolioNo(String folioNo) {
        return postTransactionRepository.findByFolioNo(folioNo);
    }

    public List<PostTransaction> getTransactionsByBillNo(String billNo) {
        return postTransactionRepository.findByBillNo(billNo);
    }

    public List<PostTransaction> getTransactionsByRoomId(String roomId) {
        return postTransactionRepository.findByRoomId(roomId);
    }

    public BigDecimal getTotalTransactionsByFolioNo(String folioNo) {
        BigDecimal total = postTransactionRepository.getTotalTransactionsByFolioNo(folioNo);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalTransactionsByBillNo(String billNo) {
        BigDecimal total = postTransactionRepository.getTotalTransactionsByBillNo(billNo);
        return total != null ? total : BigDecimal.ZERO;
    }

    public PostTransaction createInHouseTransaction(PostTransaction transaction) {
        validateTransaction(transaction);
        
        if (transaction.getFolioNo() == null || transaction.getFolioNo().trim().isEmpty()) {
            throw new BadRequestException("Folio number is required for in-house guest transaction");
        }
        
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setAuditDate(LocalDate.now());
        
        return postTransactionRepository.save(transaction);
    }

    public PostTransaction createCheckoutTransaction(PostTransaction transaction) {
        validateTransaction(transaction);
        
        if (transaction.getBillNo() == null || transaction.getBillNo().trim().isEmpty()) {
            throw new BadRequestException("Bill number is required for checkout guest transaction");
        }
        
        transaction.setTransactionId(UUID.randomUUID().toString());
        // For checkout transactions, audit date is the entered date
        transaction.setAuditDate(transaction.getDate());
        
        return postTransactionRepository.save(transaction);
    }

    public PostTransaction updateTransaction(String transactionId, PostTransaction updatedTransaction) {
        PostTransaction existingTransaction = getTransactionById(transactionId);
        
        validateTransaction(updatedTransaction);
        
        existingTransaction.setGuestName(updatedTransaction.getGuestName());
        existingTransaction.setDate(updatedTransaction.getDate());
        existingTransaction.setAccHeadId(updatedTransaction.getAccHeadId());
        existingTransaction.setVoucherNo(updatedTransaction.getVoucherNo());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setNarration(updatedTransaction.getNarration());
        
        return postTransactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(String transactionId) {
        PostTransaction transaction = getTransactionById(transactionId);
        postTransactionRepository.delete(transaction);
    }

    public List<PostTransaction> getTransactionsByAuditDate(LocalDate auditDate) {
        return postTransactionRepository.findByAuditDate(auditDate);
    }

    public void postRoomChargesForAuditDate(LocalDate auditDate) {
        // This method would implement the audit date change process
        // to post room charges and taxes for all in-house guests
        // Implementation would depend on specific business rules
    }

    private void validateTransaction(PostTransaction transaction) {
        if (transaction.getGuestName() == null || transaction.getGuestName().trim().isEmpty()) {
            throw new BadRequestException("Guest name is required");
        }
        
        if (transaction.getDate() == null) {
            throw new BadRequestException("Date is required");
        }
        
        if (transaction.getAccHeadId() == null || transaction.getAccHeadId().trim().isEmpty()) {
            throw new BadRequestException("Account head is required");
        }
        
        if (transaction.getAmount() == null) {
            throw new BadRequestException("Amount is required");
        }
    }
}
