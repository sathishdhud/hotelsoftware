package com.hotelmanagement.service;

import com.hotelmanagement.entity.CheckIn;
import com.hotelmanagement.entity.Reservation;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private NumberGenerationService numberGenerationService;

    public List<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }

    public CheckIn getCheckInById(String folioNo) {
        return checkInRepository.findById(folioNo)
                .orElseThrow(() -> new ResourceNotFoundException("Check-in not found with folio number: " + folioNo));
    }

    public List<CheckIn> getCheckInsByGuestName(String guestName) {
        return checkInRepository.findByGuestNameContaining(guestName);
    }

    public List<CheckIn> getInHouseGuests() {
        return checkInRepository.findInHouseGuests(LocalDate.now());
    }

    public List<CheckIn> getCheckOutsForDate(LocalDate date) {
        return checkInRepository.findCheckOutsForDate(date);
    }

    public List<CheckIn> getWalkInGuests() {
        return checkInRepository.findWalkInGuests();
    }

    public CheckIn checkInWithReservation(String reservationNo, String roomId) {
        // Validate reservation
        Reservation reservation = reservationService.getReservationById(reservationNo);
        
        if (!reservationService.canCheckInMoreRooms(reservationNo)) {
            throw new BadRequestException("All rooms for this reservation are already checked in");
        }

        // Validate and update room status
        Room room = roomService.getRoomById(roomId);
        if (!"VR".equals(room.getStatus())) {
            throw new BadRequestException("Room is not available for check-in");
        }

        // Create check-in record
        CheckIn checkIn = new CheckIn();
        checkIn.setFolioNo(numberGenerationService.generateFolioNumber());
        checkIn.setReservationNo(reservationNo);
        checkIn.setGuestName(reservation.getGuestName());
        checkIn.setRoomId(roomId);
        checkIn.setArrivalDate(reservation.getArrivalDate());
        checkIn.setDepartureDate(reservation.getDepartureDate());
        checkIn.setMobileNumber(reservation.getMobileNumber());
        checkIn.setEmailId(reservation.getEmailId());
        checkIn.setRate(reservation.getRate());
        checkIn.setRemarks(reservation.getRemarks());
        checkIn.setAuditDate(LocalDate.now());
        checkIn.setWalkIn("N");

        // Save check-in
        CheckIn savedCheckIn = checkInRepository.save(checkIn);

        // Update room status to occupied dirty
        roomService.updateRoomStatus(roomId, "OD");

        // Increment rooms checked in for reservation
        reservationService.incrementRoomsCheckedIn(reservationNo);

        return savedCheckIn;
    }

    public CheckIn walkInCheckIn(CheckIn checkInData, String roomId) {
        validateCheckInData(checkInData);

        // Validate and update room status
        Room room = roomService.getRoomById(roomId);
        if (!"VR".equals(room.getStatus())) {
            throw new BadRequestException("Room is not available for check-in");
        }

        // Set walk-in specific data
        checkInData.setFolioNo(numberGenerationService.generateFolioNumber());
        checkInData.setRoomId(roomId);
        checkInData.setAuditDate(LocalDate.now());
        checkInData.setWalkIn("Y");
        checkInData.setReservationNo(null); // No reservation for walk-in

        // Save check-in
        CheckIn savedCheckIn = checkInRepository.save(checkInData);

        // Update room status to occupied dirty
        roomService.updateRoomStatus(roomId, "OD");

        return savedCheckIn;
    }

    public CheckIn updateCheckIn(String folioNo, CheckIn updatedCheckIn) {
        CheckIn existingCheckIn = getCheckInById(folioNo);
        
        validateCheckInData(updatedCheckIn);

        // Update fields
        existingCheckIn.setGuestName(updatedCheckIn.getGuestName());
        existingCheckIn.setArrivalDate(updatedCheckIn.getArrivalDate());
        existingCheckIn.setDepartureDate(updatedCheckIn.getDepartureDate());
        existingCheckIn.setMobileNumber(updatedCheckIn.getMobileNumber());
        existingCheckIn.setEmailId(updatedCheckIn.getEmailId());
        existingCheckIn.setRate(updatedCheckIn.getRate());
        existingCheckIn.setRemarks(updatedCheckIn.getRemarks());

        return checkInRepository.save(existingCheckIn);
    }

    public long getInHouseGuestCount() {
        return checkInRepository.countInHouseGuests(LocalDate.now());
    }

    private void validateCheckInData(CheckIn checkIn) {
        if (checkIn.getGuestName() == null || checkIn.getGuestName().trim().isEmpty()) {
            throw new BadRequestException("Guest name is required");
        }
        
        if (checkIn.getArrivalDate() == null) {
            throw new BadRequestException("Arrival date is required");
        }
        
        if (checkIn.getDepartureDate() == null) {
            throw new BadRequestException("Departure date is required");
        }
        
        if (checkIn.getArrivalDate().isAfter(checkIn.getDepartureDate())) {
            throw new BadRequestException("Arrival date cannot be after departure date");
        }
        
        if (checkIn.getMobileNumber() == null || checkIn.getMobileNumber().trim().isEmpty()) {
            throw new BadRequestException("Mobile number is required");
        }
    }
}
