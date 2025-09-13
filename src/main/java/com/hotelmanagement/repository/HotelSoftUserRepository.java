package com.hotelmanagement.repository;

import com.hotelmanagement.entity.HotelSoftUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelSoftUserRepository extends JpaRepository<HotelSoftUser, String> {
    
    Optional<HotelSoftUser> findByUserName(String userName);
    
    List<HotelSoftUser> findByUserTypeId(String userTypeId);
    
    List<HotelSoftUser> findByHotelId(String hotelId);
    
    @Query("SELECT u FROM HotelSoftUser u WHERE u.userName = :userName AND u.password = :password")
    Optional<HotelSoftUser> findByUserNameAndPassword(@Param("userName") String userName, 
                                                      @Param("password") String password);
    
    @Query("SELECT u FROM HotelSoftUser u WHERE u.hotelId = :hotelId AND u.userTypeId = :userTypeId")
    List<HotelSoftUser> findByHotelIdAndUserTypeId(@Param("hotelId") String hotelId, 
                                                   @Param("userTypeId") String userTypeId);
    
    boolean existsByUserName(String userName);
}
