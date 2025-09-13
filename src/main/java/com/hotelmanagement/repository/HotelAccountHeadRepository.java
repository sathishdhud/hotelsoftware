package com.hotelmanagement.repository;

import com.hotelmanagement.entity.HotelAccountHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelAccountHeadRepository extends JpaRepository<HotelAccountHead, String> {
    
    Optional<HotelAccountHead> findByName(String name);
    
    @Query("SELECT hah FROM HotelAccountHead hah WHERE hah.name LIKE %:name%")
    List<HotelAccountHead> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT hah FROM HotelAccountHead hah ORDER BY hah.name")
    List<HotelAccountHead> findAllOrderByName();
}
