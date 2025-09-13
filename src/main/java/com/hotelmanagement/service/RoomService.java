package com.hotelmanagement.service;

import com.hotelmanagement.entity.Room;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
    }

    public Room getRoomByRoomNo(String roomNo) {
        return roomRepository.findByRoomNo(roomNo)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with room number: " + roomNo));
    }

    public List<Room> getRoomsByStatus(String status) {
        return roomRepository.findByStatus(status);
    }

    public List<Room> getVacantRooms() {
        return roomRepository.findVacantRooms();
    }

    public List<Room> getOccupiedRooms() {
        List<Room> occupiedDirty = roomRepository.findOccupiedDirtyRooms();
        List<Room> occupiedInspected = roomRepository.findOccupiedInspectedRooms();
        occupiedDirty.addAll(occupiedInspected);
        return occupiedDirty;
    }

    public List<Room> getBlockedRooms() {
        return roomRepository.findBlockedRooms();
    }

    public List<Room> getRoomsByFloor(String floor) {
        return roomRepository.findByFloor(floor);
    }

    public List<Room> getRoomsByRoomType(String roomTypeId) {
        return roomRepository.findByRoomTypeId(roomTypeId);
    }

    public Room createRoom(Room room) {
        validateRoom(room);
        
        // Check if room number already exists
        if (roomRepository.findByRoomNo(room.getRoomNo()).isPresent()) {
            throw new BadRequestException("Room number already exists: " + room.getRoomNo());
        }
        
        return roomRepository.save(room);
    }

    public Room updateRoom(String roomId, Room updatedRoom) {
        Room existingRoom = getRoomById(roomId);
        
        validateRoom(updatedRoom);
        
        // Check if room number is being changed and if it already exists
        if (!existingRoom.getRoomNo().equals(updatedRoom.getRoomNo())) {
            if (roomRepository.findByRoomNo(updatedRoom.getRoomNo()).isPresent()) {
                throw new BadRequestException("Room number already exists: " + updatedRoom.getRoomNo());
            }
        }
        
        existingRoom.setRoomNo(updatedRoom.getRoomNo());
        existingRoom.setFloor(updatedRoom.getFloor());
        existingRoom.setStatus(updatedRoom.getStatus());
        existingRoom.setRoomTypeId(updatedRoom.getRoomTypeId());
        existingRoom.setAdditionalInfo(updatedRoom.getAdditionalInfo());
        
        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(String roomId) {
        Room room = getRoomById(roomId);
        
        // Check if room is occupied
        if ("OD".equals(room.getStatus()) || "OI".equals(room.getStatus())) {
            throw new BadRequestException("Cannot delete occupied room");
        }
        
        roomRepository.delete(room);
    }

    @Transactional
    public void updateRoomStatus(String roomId, String status) {
        validateRoomStatus(status);
        
        int updated = roomRepository.updateRoomStatus(roomId, status);
        if (updated == 0) {
            throw new ResourceNotFoundException("Room not found with ID: " + roomId);
        }
    }

    public long getVacantRoomCount() {
        return roomRepository.countVacantRooms();
    }

    public long getOccupiedRoomCount() {
        return roomRepository.countOccupiedRooms();
    }

    private void validateRoom(Room room) {
        if (room.getRoomNo() == null || room.getRoomNo().trim().isEmpty()) {
            throw new BadRequestException("Room number is required");
        }
        
        if (room.getFloor() == null || room.getFloor().trim().isEmpty()) {
            throw new BadRequestException("Floor is required");
        }
        
        if (room.getStatus() == null || room.getStatus().trim().isEmpty()) {
            throw new BadRequestException("Status is required");
        }
        
        validateRoomStatus(room.getStatus());
    }

    private void validateRoomStatus(String status) {
        if (!List.of("VR", "OD", "OI", "Blocked").contains(status)) {
            throw new BadRequestException("Invalid room status. Must be one of: VR, OD, OI, Blocked");
        }
    }
}
