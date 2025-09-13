package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Advance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdvanceRepository extends JpaRepository<Advance, String> {
    
    List<Advance> findByFolioNo(String folioNo);
    
    List<Advance> findByReservationNo(String reservationNo);
    
    List<Advance> findByBillNo(String billNo);
    
    List<Advance> findByGuestName(String guestName);
    
    List<Advance> findByDate(LocalDate date);
    
    List<Advance> findByModeOfPaymentId(String modeOfPaymentId);
    
    @Query("SELECT a FROM Advance a WHERE a.guestName LIKE %:name%")
    List<Advance> findByGuestNameContaining(@Param("name") String name);
    
    @Query("SELECT a FROM Advance a WHERE a.date BETWEEN :startDate AND :endDate")
    List<Advance> findAdvancesBetweenDates(@Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(a.amount) FROM Advance a WHERE a.folioNo = :folioNo")
    BigDecimal getTotalAdvancesByFolioNo(@Param("folioNo") String folioNo);
    
    @Query("SELECT SUM(a.amount) FROM Advance a WHERE a.reservationNo = :reservationNo")
    BigDecimal getTotalAdvancesByReservationNo(@Param("reservationNo") String reservationNo);
    
    @Query("SELECT SUM(a.amount) FROM Advance a WHERE a.billNo = :billNo")
    BigDecimal getTotalAdvancesByBillNo(@Param("billNo") String billNo);
    
    @Query("SELECT a FROM Advance a WHERE a.shiftDate = :shiftDate AND a.shiftNo = :shiftNo")
    List<Advance> findByShiftDateAndShiftNo(@Param("shiftDate") LocalDate shiftDate, 
                                           @Param("shiftNo") String shiftNo);
    
    @Query("SELECT SUM(a.amount) FROM Advance a WHERE a.date = :date")
    BigDecimal getTotalAdvancesForDate(@Param("date") LocalDate date);
}
