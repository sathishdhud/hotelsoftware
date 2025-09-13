package com.hotelmanagement.repository;

import com.hotelmanagement.entity.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanTypeRepository extends JpaRepository<PlanType, String> {
    
    Optional<PlanType> findByPlanName(String planName);
    
    @Query("SELECT pt FROM PlanType pt WHERE pt.planName LIKE %:name%")
    List<PlanType> findByPlanNameContaining(@Param("name") String name);
    
    @Query("SELECT pt FROM PlanType pt ORDER BY pt.planName")
    List<PlanType> findAllOrderByPlanName();
}
