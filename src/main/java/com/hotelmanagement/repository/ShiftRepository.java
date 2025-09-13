package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, String> {
    
    List<Shift> findByShiftDate(LocalDate shiftDate);
    
    @Query("SELECT s FROM Shift s WHERE s.shiftDate BETWEEN :startDate AND :endDate")
    List<Shift> findShiftsBetweenDates(@Param("startDate") LocalDate startDate, 
                                      @Param("endDate") LocalDate endDate);
    
    @Query("SELECT s FROM Shift s WHERE s.shiftDate = :shiftDate ORDER BY s.shiftNo DESC")
    List<Shift> findByShiftDateOrderByShiftNoDesc(@Param("shiftDate") LocalDate shiftDate);
    
    @Query("SELECT s FROM Shift s WHERE s.shiftDate = :shiftDate AND s.shiftNo = :shiftNo")
    Optional<Shift> findByShiftDateAndShiftNo(@Param("shiftDate") LocalDate shiftDate, 
                                             @Param("shiftNo") String shiftNo);
    
    @Query("SELECT s FROM Shift s ORDER BY s.shiftDate DESC, s.shiftNo DESC")
    List<Shift> findAllOrderByDateAndShiftDesc();
}
