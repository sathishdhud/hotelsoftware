package com.hotelmanagement.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NumberGenerationService {

    private final AtomicLong reservationCounter = new AtomicLong(1);
    private final AtomicLong folioCounter = new AtomicLong(1);
    private final AtomicLong billCounter = new AtomicLong(1);
    private final AtomicLong receiptCounter = new AtomicLong(1);

    public String generateReservationNumber() {
        return generateNumber("R", reservationCounter);
    }

    public String generateFolioNumber() {
        return generateNumber("F", folioCounter);
    }

    public String generateBillNumber() {
        return generateNumber("B", billCounter);
    }

    public String generateReceiptNumber() {
        return generateNumber("RC", receiptCounter);
    }

    private String generateNumber(String prefix, AtomicLong counter) {
        LocalDate now = LocalDate.now();
        String year = now.format(DateTimeFormatter.ofPattern("yy"));
        String nextYear = now.plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        
        long number = counter.getAndIncrement();
        
        // Format: PREFIX + NUMBER/YY-YY (e.g., R001/24-25)
        return String.format("%s%03d/%s-%s", prefix, number, year, nextYear);
    }

    // Method to reset counters (typically called at the start of a new accounting year)
    public void resetCounters() {
        reservationCounter.set(1);
        folioCounter.set(1);
        billCounter.set(1);
        receiptCounter.set(1);
    }
}
