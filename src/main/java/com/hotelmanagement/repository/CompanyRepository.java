package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    
    Optional<Company> findByCompanyName(String companyName);
    
    List<Company> findByGstNumber(String gstNumber);
    
    @Query("SELECT c FROM Company c WHERE c.companyName LIKE %:name%")
    List<Company> findByCompanyNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM Company c WHERE c.gstNumber IS NOT NULL AND c.gstNumber != ''")
    List<Company> findCompaniesWithGst();
}
