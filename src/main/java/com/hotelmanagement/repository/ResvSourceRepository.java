package com.hotelmanagement.repository;

import com.hotelmanagement.entity.ResvSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResvSourceRepository extends JpaRepository<ResvSource, String> {
    
    Optional<ResvSource> findByResvSource(String resvSource);
    
    @Query("SELECT rs FROM ResvSource rs WHERE rs.resvSource LIKE %:source%")
    List<ResvSource> findByResvSourceContaining(@Param("source") String source);
    
    @Query("SELECT rs FROM ResvSource rs ORDER BY rs.resvSource")
    List<ResvSource> findAllOrderByResvSource();
}
