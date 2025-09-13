package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Taxation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaxationRepository extends JpaRepository<Taxation, String> {
    
    Optional<Taxation> findByTaxName(String taxName);
    
    List<Taxation> findByPercentage(BigDecimal percentage);
    
    @Query("SELECT t FROM Taxation t WHERE t.taxName LIKE %:name%")
    List<Taxation> findByTaxNameContaining(@Param("name") String name);
    
    @Query("SELECT t FROM Taxation t WHERE t.percentage > :minPercentage")
    List<Taxation> findByPercentageGreaterThan(@Param("minPercentage") BigDecimal minPercentage);
    
    @Query("SELECT t FROM Taxation t ORDER BY t.percentage")
    List<Taxation> findAllOrderByPercentage();
}
