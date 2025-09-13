package com.hotelmanagement.service;

import com.hotelmanagement.entity.Shift;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public Shift getShiftById(String shiftNo) {
        return shiftRepository.findById(shiftNo)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with number: " + shiftNo));
    }

    public List<Shift> getShiftsByDate(LocalDate shiftDate) {
        return shiftRepository.findByShiftDate(shiftDate);
    }

    public Shift createOrUpdateShift(LocalDate shiftDate, String shiftNo, BigDecimal balance) {
        Optional<Shift> existingShift = shiftRepository.findByShiftDateAndShiftNo(shiftDate, shiftNo);
        
        if (existingShift.isPresent()) {
            // Update existing shift
            Shift shift = existingShift.get();
            shift.setBalance(balance);
            return shiftRepository.save(shift);
        } else {
            // Create new shift
            Shift newShift = new Shift();
            newShift.setShiftNo(shiftNo);
            newShift.setShiftDate(shiftDate);
            newShift.setBalance(balance);
            return shiftRepository.save(newShift);
        }
    }

    public String getCurrentShiftNo() {
        // Simple implementation - in real scenario, this would be based on current time
        LocalDate today = LocalDate.now();
        List<Shift> todayShifts = shiftRepository.findByShiftDateOrderByShiftNoDesc(today);
        
        if (todayShifts.isEmpty()) {
            return "1";
        } else {
            try {
                int lastShiftNo = Integer.parseInt(todayShifts.get(0).getShiftNo());
                return String.valueOf(lastShiftNo);
            } catch (NumberFormatException e) {
                return "1";
            }
        }
    }

    public void processShiftChange(LocalDate shiftDate, String shiftNo, BigDecimal balance) {
        createOrUpdateShift(shiftDate, shiftNo, balance);
    }
}
