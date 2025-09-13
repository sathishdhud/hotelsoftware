package com.hotelmanagement.service;

import com.hotelmanagement.entity.Advance;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.AdvanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AdvanceService {

    @Autowired
    private AdvanceRepository advanceRepository;

    @Autowired
    private NumberGenerationService numberGenerationService;

    @Autowired
    private ShiftService shiftService;

    public List<Advance> getAllAdvances() {
        return advanceRepository.findAll();
    }

    public Advance getAdvanceById(String receiptNo) {
        return advanceRepository.findById(receiptNo)
                .orElseThrow(() -> new ResourceNotFoundException("Advance not found with receipt number: " + receiptNo));
    }

    public List<Advance> getAdvancesByFolioNo(String folioNo) {
        return advanceRepository.findByFolioNo(folioNo);
    }

    public List<Advance> getAdvancesByReservationNo(String reservationNo) {
        return advanceRepository.findByReservationNo(reservationNo);
    }

    public List<Advance> getAdvancesByBillNo(String billNo) {
        return advanceRepository.findByBillNo(billNo);
    }

    public BigDecimal getTotalAdvancesByFolioNo(String folioNo) {
        BigDecimal total = advanceRepository.getTotalAdvancesByFolioNo(folioNo);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalAdvancesByReservationNo(String reservationNo) {
        BigDecimal total = advanceRepository.getTotalAdvancesByReservationNo(reservationNo);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalAdvancesByBillNo(String billNo) {
        BigDecimal total = advanceRepository.getTotalAdvancesByBillNo(billNo);
        return total != null ? total : BigDecimal.ZERO;
    }

    public Advance createAdvanceForReservation(Advance advance) {
        validateAdvance(advance);
        
        if (advance.getReservationNo() == null || advance.getReservationNo().trim().isEmpty()) {
            throw new BadRequestException("Reservation number is required for reservation advance");
        }
        
        return createAdvanceInternal(advance);
    }

    public Advance createAdvanceForInHouseGuest(Advance advance) {
        validateAdvance(advance);
        
        if (advance.getFolioNo() == null || advance.getFolioNo().trim().isEmpty()) {
            throw new BadRequestException("Folio number is required for in-house guest advance");
        }
        
        return createAdvanceInternal(advance);
    }

    public Advance createAdvanceForCheckedOutGuest(Advance advance) {
        validateAdvance(advance);
        
        if (advance.getBillNo() == null || advance.getBillNo().trim().isEmpty()) {
            throw new BadRequestException("Bill number is required for checked-out guest advance");
        }
        
        return createAdvanceInternal(advance);
    }

    private Advance createAdvanceInternal(Advance advance) {
        // Generate receipt number
        advance.setReceiptNo(numberGenerationService.generateReceiptNumber());
        
        // Set audit date and shift information
        advance.setAuditDate(LocalDate.now());
        advance.setShiftDate(LocalDate.now());
        advance.setShiftNo(shiftService.getCurrentShiftNo());
        
        return advanceRepository.save(advance);
    }

    public Advance updateAdvance(String receiptNo, Advance updatedAdvance) {
        Advance existingAdvance = getAdvanceById(receiptNo);
        
        validateAdvance(updatedAdvance);
        
        existingAdvance.setGuestName(updatedAdvance.getGuestName());
        existingAdvance.setDate(updatedAdvance.getDate());
        existingAdvance.setModeOfPaymentId(updatedAdvance.getModeOfPaymentId());
        existingAdvance.setAmount(updatedAdvance.getAmount());
        existingAdvance.setCreditCardCompany(updatedAdvance.getCreditCardCompany());
        existingAdvance.setCardNumber(updatedAdvance.getCardNumber());
        existingAdvance.setOnlineCompanyName(updatedAdvance.getOnlineCompanyName());
        existingAdvance.setDetails(updatedAdvance.getDetails());
        existingAdvance.setNarration(updatedAdvance.getNarration());
        
        return advanceRepository.save(existingAdvance);
    }

    public void deleteAdvance(String receiptNo) {
        Advance advance = getAdvanceById(receiptNo);
        advanceRepository.delete(advance);
    }

    public BigDecimal getTotalAdvancesForDate(LocalDate date) {
        BigDecimal total = advanceRepository.getTotalAdvancesForDate(date);
        return total != null ? total : BigDecimal.ZERO;
    }

    private void validateAdvance(Advance advance) {
        if (advance.getGuestName() == null || advance.getGuestName().trim().isEmpty()) {
            throw new BadRequestException("Guest name is required");
        }
        
        if (advance.getDate() == null) {
            throw new BadRequestException("Date is required");
        }
        
        if (advance.getModeOfPaymentId() == null || advance.getModeOfPaymentId().trim().isEmpty()) {
            throw new BadRequestException("Mode of payment is required");
        }
        
        if (advance.getAmount() != null && advance.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Amount cannot be negative");
        }
    }
}
