package com.hotelmanagement.service;

import com.hotelmanagement.entity.FoBill;
import com.hotelmanagement.entity.CheckIn;
import com.hotelmanagement.entity.PostTransaction;
import com.hotelmanagement.entity.Advance;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.FoBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BillingService {

    @Autowired
    private FoBillRepository foBillRepository;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private PostTransactionService postTransactionService;

    @Autowired
    private AdvanceService advanceService;

    @Autowired
    private NumberGenerationService numberGenerationService;

    @Autowired
    private RoomService roomService;

    public List<FoBill> getAllBills() {
        return foBillRepository.findAll();
    }

    public FoBill getBillById(String billNo) {
        return foBillRepository.findById(billNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with number: " + billNo));
    }

    public FoBill getBillByFolioNo(String folioNo) {
        return foBillRepository.findByFolioNo(folioNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found for folio number: " + folioNo));
    }

    public List<FoBill> getBillsByGuestName(String guestName) {
        return foBillRepository.findByGuestNameContaining(guestName);
    }

    public FoBill generateBill(String folioNo) {
        // Get check-in details
        CheckIn checkIn = checkInService.getCheckInById(folioNo);
        
        // Calculate total transaction amount
        BigDecimal totalTransactions = postTransactionService.getTotalTransactionsByFolioNo(folioNo);
        
        // Calculate total advance amount
        BigDecimal totalAdvances = advanceService.getTotalAdvancesByFolioNo(folioNo);
        
        // Create bill
        FoBill bill = new FoBill();
        bill.setBillNo(numberGenerationService.generateBillNumber());
        bill.setFolioNo(folioNo);
        bill.setGuestName(checkIn.getGuestName());
        bill.setRoomId(checkIn.getRoomId());
        bill.setTotalAmount(totalTransactions);
        bill.setAdvanceAmount(totalAdvances);
        
        FoBill savedBill = foBillRepository.save(bill);
        
        // Update room status to vacant ready after checkout
        roomService.updateRoomStatus(checkIn.getRoomId(), "VR");
        
        return savedBill;
    }

    public FoBill createSplitBill(String originalBillNo, List<String> transactionIds) {
        FoBill originalBill = getBillById(originalBillNo);
        
        if (transactionIds == null || transactionIds.isEmpty()) {
            throw new BadRequestException("Transaction IDs are required for split bill");
        }
        
        // Calculate amount for selected transactions
        BigDecimal splitAmount = BigDecimal.ZERO;
        for (String transactionId : transactionIds) {
            PostTransaction transaction = postTransactionService.getTransactionById(transactionId);
            if (transaction.getAmount() != null) {
                splitAmount = splitAmount.add(transaction.getAmount());
            }
        }
        
        // Create new split bill
        FoBill splitBill = new FoBill();
        splitBill.setBillNo(numberGenerationService.generateBillNumber());
        splitBill.setFolioNo(originalBill.getFolioNo());
        splitBill.setGuestName(originalBill.getGuestName());
        splitBill.setRoomId(originalBill.getRoomId());
        splitBill.setTotalAmount(splitAmount);
        splitBill.setAdvanceAmount(BigDecimal.ZERO); // Split bills typically don't include advances
        
        FoBill savedSplitBill = foBillRepository.save(splitBill);
        
        // Update original bill amount
        BigDecimal newOriginalAmount = originalBill.getTotalAmount().subtract(splitAmount);
        originalBill.setTotalAmount(newOriginalAmount);
        foBillRepository.save(originalBill);
        
        return savedSplitBill;
    }

    public BigDecimal getTotalBillAmountForDate(LocalDateTime date) {
        BigDecimal total = foBillRepository.getTotalBillAmountForDate(date);
        return total != null ? total : BigDecimal.ZERO;
    }

    public long getBillCountForDate(LocalDateTime date) {
        return foBillRepository.countBillsForDate(date);
    }

    public void deleteBill(String billNo) {
        FoBill bill = getBillById(billNo);
        foBillRepository.delete(bill);
    }
}
