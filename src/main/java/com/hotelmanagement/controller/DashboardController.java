package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Room statistics
        stats.put("totalRooms", roomService.getAllRooms().size());
        stats.put("vacantRooms", roomService.getVacantRoomCount());
        stats.put("occupiedRooms", roomService.getOccupiedRoomCount());
        stats.put("blockedRooms", roomService.getBlockedRooms().size());
        
        // Guest statistics
        stats.put("inHouseGuests", checkInService.getInHouseGuestCount());
        stats.put("walkInGuests", checkInService.getWalkInGuests().size());
        
        // Today's statistics
        LocalDate today = LocalDate.now();
        stats.put("todayArrivals", reservationService.getExpectedArrivals(today).size());
        stats.put("todayDepartures", reservationService.getExpectedDepartures(today).size());
        stats.put("todayCheckOuts", checkInService.getCheckOutsForDate(today).size());
        
        // Pending operations
        stats.put("pendingCheckIns", reservationService.getPendingCheckIns().size());
        
        return ResponseEntity.ok(ApiResponse.success("Dashboard statistics retrieved", stats));
    }

    @GetMapping("/room-status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRoomStatusSummary() {
        Map<String, Object> roomStatus = new HashMap<>();
        
        roomStatus.put("vacant", roomService.getVacantRooms());
        roomStatus.put("occupied", roomService.getOccupiedRooms());
        roomStatus.put("blocked", roomService.getBlockedRooms());
        
        return ResponseEntity.ok(ApiResponse.success("Room status summary retrieved", roomStatus));
    }

    @GetMapping("/occupancy-rate")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOccupancyRate() {
        long totalRooms = roomService.getAllRooms().size();
        long occupiedRooms = roomService.getOccupiedRoomCount();
        
        double occupancyRate = totalRooms > 0 ? (double) occupiedRooms / totalRooms * 100 : 0;
        
        Map<String, Object> occupancy = new HashMap<>();
        occupancy.put("totalRooms", totalRooms);
        occupancy.put("occupiedRooms", occupiedRooms);
        occupancy.put("occupancyRate", Math.round(occupancyRate * 100.0) / 100.0);
        
        return ResponseEntity.ok(ApiResponse.success("Occupancy rate calculated", occupancy));
    }
}
