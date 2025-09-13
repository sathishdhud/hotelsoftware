package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    
    List<Reservation> findByGuestName(String guestName);
    
    List<Reservation> findByMobileNumber(String mobileNumber);
    
    List<Reservation> findByArrivalDate(LocalDate arrivalDate);
    
    List<Reservation> findByDepartureDate(LocalDate departureDate);
    
    List<Reservation> findByCompanyId(String companyId);
    
    List<Reservation> findByRoomTypeId(String roomTypeId);
    
    @Query("SELECT r FROM Reservation r WHERE r.arrivalDate = :date")
    List<Reservation> findExpectedArrivals(@Param("date") LocalDate date);
    
    @Query("SELECT r FROM Reservation r WHERE r.departureDate = :date")
    List<Reservation> findExpectedDepartures(@Param("date") LocalDate date);
    
    @Query("SELECT r FROM Reservation r WHERE r.arrivalDate BETWEEN :startDate AND :endDate")
    List<Reservation> findReservationsBetweenDates(@Param("startDate") LocalDate startDate, 
                                                   @Param("endDate") LocalDate endDate);
    
    @Query("SELECT r FROM Reservation r WHERE r.guestName LIKE %:name%")
    List<Reservation> findByGuestNameContaining(@Param("name") String name);
    
    @Query("SELECT r FROM Reservation r WHERE r.roomsCheckedIn < r.noOfRooms")
    List<Reservation> findPendingCheckIns();
    
    @Modifying
    @Query("UPDATE Reservation r SET r.roomsCheckedIn = r.roomsCheckedIn + 1 WHERE r.reservationNo = :reservationNo")
    int incrementRoomsCheckedIn(@Param("reservationNo") String reservationNo);
    
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.arrivalDate = :date")
    long countArrivalsForDate(@Param("date") LocalDate date);
    
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.departureDate = :date")
    long countDeparturesForDate(@Param("date") LocalDate date);
}
