package com.hotelmanagement.repository;

import com.hotelmanagement.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, String> {
    
    Optional<UserType> findByTypeName(String typeName);
    
    @Query("SELECT ut FROM UserType ut WHERE ut.typeName LIKE %:name%")
    List<UserType> findByTypeNameContaining(@Param("name") String name);
    
    @Query("SELECT ut FROM UserType ut ORDER BY ut.typeName")
    List<UserType> findAllOrderByTypeName();
}
