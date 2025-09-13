package com.hotelmanagement.repository;

import com.hotelmanagement.entity.RefMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefModeRepository extends JpaRepository<RefMode, String> {
    
    Optional<RefMode> findByRefMode(String refMode);
    
    @Query("SELECT rm FROM RefMode rm WHERE rm.refMode LIKE %:mode%")
    List<RefMode> findByRefModeContaining(@Param("mode") String mode);
    
    @Query("SELECT rm FROM RefMode rm ORDER BY rm.refMode")
    List<RefMode> findAllOrderByRefMode();
}
