package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, String> {
    
    Optional<Nationality> findByNationality(String nationality);
    
    @Query("SELECT n FROM Nationality n WHERE n.nationality LIKE %:nationality%")
    List<Nationality> findByNationalityContaining(@Param("nationality") String nationality);
    
    @Query("SELECT n FROM Nationality n ORDER BY n.nationality")
    List<Nationality> findAllOrderByNationality();
}
