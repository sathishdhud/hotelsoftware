package com.hotelmanagement.repository;

import com.hotelmanagement.entity.ArrivalMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArrivalModeRepository extends JpaRepository<ArrivalMode, String> {
    
    Optional<ArrivalMode> findByArrivalMode(String arrivalMode);
    
    @Query("SELECT am FROM ArrivalMode am WHERE am.arrivalMode LIKE %:mode%")
    List<ArrivalMode> findByArrivalModeContaining(@Param("mode") String mode);
    
    @Query("SELECT am FROM ArrivalMode am ORDER BY am.arrivalMode")
    List<ArrivalMode> findAllOrderByArrivalMode();
}
