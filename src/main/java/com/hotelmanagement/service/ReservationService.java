package com.hotelmanagement.service;

import com.hotelmanagement.entity.Reservation;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private NumberGenerationService numberGenerationService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(String reservationNo) {
        return reservationRepository.findById(reservationNo)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + reservationNo));
    }

    public List<Reservation> getReservationsByGuestName(String guestName) {
        return reservationRepository.findByGuestNameContaining(guestName);
    }

    public List<Reservation> getReservationsByMobileNumber(String mobileNumber) {
        return reservationRepository.findByMobileNumber(mobileNumber);
    }

    public List<Reservation> getExpectedArrivals(LocalDate date) {
        return reservationRepository.findExpectedArrivals(date);
    }

    public List<Reservation> getExpectedDepartures(LocalDate date) {
        return reservationRepository.findExpectedDepartures(date);
    }

    public List<Reservation> getPendingCheckIns() {
        return reservationRepository.findPendingCheckIns();
    }

    public Reservation createReservation(Reservation reservation) {
        validateReservation(reservation);
        
        // Generate reservation number
        String reservationNo = numberGenerationService.generateReservationNumber();
        reservation.setReservationNo(reservationNo);
        
        // Calculate number of days
        long days = ChronoUnit.DAYS.between(reservation.getArrivalDate(), reservation.getDepartureDate());
        reservation.setNoOfDays((int) days);
        
        // Initialize rooms checked in to 0
        reservation.setRoomsCheckedIn(0);
        
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(String reservationNo, Reservation updatedReservation) {
        Reservation existingReservation = getReservationById(reservationNo);
        
        // Check if any rooms are already checked in
        if (existingReservation.getRoomsCheckedIn() > 0) {
            throw new BadRequestException("Cannot modify reservation with checked-in rooms");
        }
        
        validateReservation(updatedReservation);
        
        // Update fields
        existingReservation.setGuestName(updatedReservation.getGuestName());
        existingReservation.setCompanyId(updatedReservation.getCompanyId());
        existingReservation.setPlanId(updatedReservation.getPlanId());
        existingReservation.setRoomTypeId(updatedReservation.getRoomTypeId());
        existingReservation.setArrivalDate(updatedReservation.getArrivalDate());
        existingReservation.setDepartureDate(updatedReservation.getDepartureDate());
        existingReservation.setNoOfPersons(updatedReservation.getNoOfPersons());
        existingReservation.setNoOfRooms(updatedReservation.getNoOfRooms());
        existingReservation.setMobileNumber(updatedReservation.getMobileNumber());
        existingReservation.setEmailId(updatedReservation.getEmailId());
        existingReservation.setRate(updatedReservation.getRate());
        existingReservation.setIncludingGst(updatedReservation.getIncludingGst());
        existingReservation.setRemarks(updatedReservation.getRemarks());
        
        // Recalculate number of days
        long days = ChronoUnit.DAYS.between(existingReservation.getArrivalDate(), existingReservation.getDepartureDate());
        existingReservation.setNoOfDays((int) days);
        
        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(String reservationNo) {
        Reservation reservation = getReservationById(reservationNo);
        
        if (reservation.getRoomsCheckedIn() > 0) {
            throw new BadRequestException("Cannot delete reservation with checked-in rooms");
        }
        
        reservationRepository.delete(reservation);
    }

    public void incrementRoomsCheckedIn(String reservationNo) {
        Reservation reservation = getReservationById(reservationNo);
        
        if (reservation.getRoomsCheckedIn() >= reservation.getNoOfRooms()) {
            throw new BadRequestException("All rooms for this reservation are already checked in");
        }
        
        reservationRepository.incrementRoomsCheckedIn(reservationNo);
    }

    public boolean canCheckInMoreRooms(String reservationNo) {
        Reservation reservation = getReservationById(reservationNo);
        return reservation.getRoomsCheckedIn() < reservation.getNoOfRooms();
    }

    private void validateReservation(Reservation reservation) {
        if (reservation.getGuestName() == null || reservation.getGuestName().trim().isEmpty()) {
            throw new BadRequestException("Guest name is required");
        }
        
        if (reservation.getArrivalDate() == null) {
            throw new BadRequestException("Arrival date is required");
        }
        
        if (reservation.getDepartureDate() == null) {
            throw new BadRequestException("Departure date is required");
        }
        
        if (reservation.getArrivalDate().isAfter(reservation.getDepartureDate())) {
            throw new BadRequestException("Arrival date cannot be after departure date");
        }
        
        if (reservation.getNoOfPersons() == null || reservation.getNoOfPersons() <= 0) {
            throw new BadRequestException("Number of persons must be greater than 0");
        }
        
        if (reservation.getNoOfRooms() == null || reservation.getNoOfRooms() <= 0) {
            throw new BadRequestException("Number of rooms must be greater than 0");
        }
        
        if (reservation.getMobileNumber() == null || reservation.getMobileNumber().trim().isEmpty()) {
            throw new BadRequestException("Mobile number is required");
        }
        
        // Set default values
        if (reservation.getIncludingGst() == null) {
            reservation.setIncludingGst("N");
        }
    }
}
