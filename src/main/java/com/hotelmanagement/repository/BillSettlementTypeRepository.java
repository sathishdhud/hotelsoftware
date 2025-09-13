package com.hotelmanagement.repository;

import com.hotelmanagement.entity.BillSettlementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillSettlementTypeRepository extends JpaRepository<BillSettlementType, String> {
    
    Optional<BillSettlementType> findByName(String name);
    
    @Query("SELECT bst FROM BillSettlementType bst WHERE bst.name LIKE %:name%")
    List<BillSettlementType> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT bst FROM BillSettlementType bst ORDER BY bst.name")
    List<BillSettlementType> findAllOrderByName();
}
