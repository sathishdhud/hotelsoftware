package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
    
    Optional<Hotel> findByHotelName(String hotelName);
    
    List<Hotel> findByCity(String city);
    
    List<Hotel> findByState(String state);
    
    @Query("SELECT h FROM Hotel h WHERE h.city = :city AND h.state = :state")
    List<Hotel> findByCityAndState(@Param("city") String city, @Param("state") String state);
    
    @Query("SELECT h FROM Hotel h WHERE h.hotelName LIKE %:name%")
    List<Hotel> findByHotelNameContaining(@Param("name") String name);
}
