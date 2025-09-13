package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    
    Optional<Room> findByRoomNo(String roomNo);
    
    List<Room> findByStatus(String status);
    
    List<Room> findByFloor(String floor);
    
    List<Room> findByRoomTypeId(String roomTypeId);
    
    @Query("SELECT r FROM Room r WHERE r.status = 'VR'")
    List<Room> findVacantRooms();
    
    @Query("SELECT r FROM Room r WHERE r.status = 'OD'")
    List<Room> findOccupiedDirtyRooms();
    
    @Query("SELECT r FROM Room r WHERE r.status = 'OI'")
    List<Room> findOccupiedInspectedRooms();
    
    @Query("SELECT r FROM Room r WHERE r.status = 'Blocked'")
    List<Room> findBlockedRooms();
    
    @Query("SELECT r FROM Room r WHERE r.floor = :floor AND r.status = :status")
    List<Room> findByFloorAndStatus(@Param("floor") String floor, @Param("status") String status);
    
    @Modifying
    @Query("UPDATE Room r SET r.status = :status WHERE r.roomId = :roomId")
    int updateRoomStatus(@Param("roomId") String roomId, @Param("status") String status);
    
    @Query("SELECT COUNT(r) FROM Room r WHERE r.status = 'VR'")
    long countVacantRooms();
    
    @Query("SELECT COUNT(r) FROM Room r WHERE r.status IN ('OD', 'OI')")
    long countOccupiedRooms();
}
