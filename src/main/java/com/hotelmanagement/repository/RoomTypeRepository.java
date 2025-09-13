package com.hotelmanagement.repository;

import com.hotelmanagement.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {
    
    Optional<RoomType> findByTypeName(String typeName);
    
    @Query("SELECT rt FROM RoomType rt WHERE rt.noOfRooms > :minRooms")
    List<RoomType> findByNoOfRoomsGreaterThan(@Param("minRooms") Integer minRooms);
    
    @Query("SELECT rt FROM RoomType rt ORDER BY rt.typeName")
    List<RoomType> findAllOrderByTypeName();
}
