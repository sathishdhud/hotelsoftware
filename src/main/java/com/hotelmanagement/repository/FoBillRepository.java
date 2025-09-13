package com.hotelmanagement.repository;

import com.hotelmanagement.entity.FoBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FoBillRepository extends JpaRepository<FoBill, String> {
    
    Optional<FoBill> findByFolioNo(String folioNo);
    
    List<FoBill> findByGuestName(String guestName);
    
    List<FoBill> findByRoomId(String roomId);
    
    @Query("SELECT fb FROM FoBill fb WHERE fb.guestName LIKE %:name%")
    List<FoBill> findByGuestNameContaining(@Param("name") String name);
    
    @Query("SELECT fb FROM FoBill fb WHERE fb.createdAt BETWEEN :startDate AND :endDate")
    List<FoBill> findBillsBetweenDates(@Param("startDate") LocalDateTime startDate, 
                                      @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(fb.totalAmount) FROM FoBill fb WHERE DATE(fb.createdAt) = :date")
    BigDecimal getTotalBillAmountForDate(@Param("date") LocalDateTime date);
    
    @Query("SELECT SUM(fb.advanceAmount) FROM FoBill fb WHERE DATE(fb.createdAt) = :date")
    BigDecimal getTotalAdvanceAmountForDate(@Param("date") LocalDateTime date);
    
    @Query("SELECT COUNT(fb) FROM FoBill fb WHERE DATE(fb.createdAt) = :date")
    long countBillsForDate(@Param("date") LocalDateTime date);
}
