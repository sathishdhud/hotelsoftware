package com.hotelmanagement.repository;

import com.hotelmanagement.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, String> {
    
    Optional<CheckIn> findByReservationNo(String reservationNo);
    
    List<CheckIn> findByGuestName(String guestName);
    
    List<CheckIn> findByRoomId(String roomId);
    
    List<CheckIn> findByMobileNumber(String mobileNumber);
    
    List<CheckIn> findByArrivalDate(LocalDate arrivalDate);
    
    List<CheckIn> findByDepartureDate(LocalDate departureDate);
    
    List<CheckIn> findByWalkIn(String walkIn);
    
    @Query("SELECT c FROM CheckIn c WHERE c.guestName LIKE %:name%")
    List<CheckIn> findByGuestNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM CheckIn c WHERE c.arrivalDate BETWEEN :startDate AND :endDate")
    List<CheckIn> findCheckInsBetweenDates(@Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate);
    
    @Query("SELECT c FROM CheckIn c WHERE c.departureDate = :date")
    List<CheckIn> findCheckOutsForDate(@Param("date") LocalDate date);
    
    @Query("SELECT c FROM CheckIn c WHERE c.arrivalDate <= :currentDate AND c.departureDate >= :currentDate")
    List<CheckIn> findInHouseGuests(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT c FROM CheckIn c WHERE c.walkIn = 'Y'")
    List<CheckIn> findWalkInGuests();
    
    @Query("SELECT COUNT(c) FROM CheckIn c WHERE c.arrivalDate <= :currentDate AND c.departureDate >= :currentDate")
    long countInHouseGuests(@Param("currentDate") LocalDate currentDate);
}
