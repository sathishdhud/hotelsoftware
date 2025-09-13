package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Room>>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(ApiResponse.success("Rooms retrieved successfully", rooms));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Room>> getRoomById(@PathVariable String roomId) {
        Room room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(ApiResponse.success("Room retrieved successfully", room));
    }

    @GetMapping("/by-number/{roomNo}")
    public ResponseEntity<ApiResponse<Room>> getRoomByRoomNo(@PathVariable String roomNo) {
        Room room = roomService.getRoomByRoomNo(roomNo);
        return ResponseEntity.ok(ApiResponse.success("Room retrieved successfully", room));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<ApiResponse<List<Room>>> getRoomsByStatus(@PathVariable String status) {
        List<Room> rooms = roomService.getRoomsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Rooms retrieved by status", rooms));
    }

    @GetMapping("/vacant")
    public ResponseEntity<ApiResponse<List<Room>>> getVacantRooms() {
        List<Room> rooms = roomService.getVacantRooms();
        return ResponseEntity.ok(ApiResponse.success("Vacant rooms retrieved", rooms));
    }

    @GetMapping("/occupied")
    public ResponseEntity<ApiResponse<List<Room>>> getOccupiedRooms() {
        List<Room> rooms = roomService.getOccupiedRooms();
        return ResponseEntity.ok(ApiResponse.success("Occupied rooms retrieved", rooms));
    }

    @GetMapping("/blocked")
    public ResponseEntity<ApiResponse<List<Room>>> getBlockedRooms() {
        List<Room> rooms = roomService.getBlockedRooms();
        return ResponseEntity.ok(ApiResponse.success("Blocked rooms retrieved", rooms));
    }

    @GetMapping("/by-floor/{floor}")
    public ResponseEntity<ApiResponse<List<Room>>> getRoomsByFloor(@PathVariable String floor) {
        List<Room> rooms = roomService.getRoomsByFloor(floor);
        return ResponseEntity.ok(ApiResponse.success("Rooms retrieved by floor", rooms));
    }

    @GetMapping("/by-type/{roomTypeId}")
    public ResponseEntity<ApiResponse<List<Room>>> getRoomsByRoomType(@PathVariable String roomTypeId) {
        List<Room> rooms = roomService.getRoomsByRoomType(roomTypeId);
        return ResponseEntity.ok(ApiResponse.success("Rooms retrieved by type", rooms));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Room>> createRoom(@Valid @RequestBody Room room) {
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Room created successfully", createdRoom));
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Room>> updateRoom(@PathVariable String roomId, @Valid @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(roomId, room);
        return ResponseEntity.ok(ApiResponse.success("Room updated successfully", updatedRoom));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok(ApiResponse.success("Room deleted successfully"));
    }

    @PatchMapping("/{roomId}/status")
    public ResponseEntity<ApiResponse<Void>> updateRoomStatus(@PathVariable String roomId, @RequestParam String status) {
        roomService.updateRoomStatus(roomId, status);
        return ResponseEntity.ok(ApiResponse.success("Room status updated successfully"));
    }

    @GetMapping("/count/vacant")
    public ResponseEntity<ApiResponse<Long>> getVacantRoomCount() {
        long count = roomService.getVacantRoomCount();
        return ResponseEntity.ok(ApiResponse.success("Vacant room count retrieved", count));
    }

    @GetMapping("/count/occupied")
    public ResponseEntity<ApiResponse<Long>> getOccupiedRoomCount() {
        long count = roomService.getOccupiedRoomCount();
        return ResponseEntity.ok(ApiResponse.success("Occupied room count retrieved", count));
    }
}
