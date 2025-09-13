package com.hotelmanagement.repository;

import com.hotelmanagement.entity.PostTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostTransactionRepository extends JpaRepository<PostTransaction, String> {
    
    List<PostTransaction> findByFolioNo(String folioNo);
    
    List<PostTransaction> findByBillNo(String billNo);
    
    List<PostTransaction> findByRoomId(String roomId);
    
    List<PostTransaction> findByGuestName(String guestName);
    
    List<PostTransaction> findByDate(LocalDate date);
    
    List<PostTransaction> findByAccHeadId(String accHeadId);
    
    @Query("SELECT pt FROM PostTransaction pt WHERE pt.guestName LIKE %:name%")
    List<PostTransaction> findByGuestNameContaining(@Param("name") String name);
    
    @Query("SELECT pt FROM PostTransaction pt WHERE pt.date BETWEEN :startDate AND :endDate")
    List<PostTransaction> findTransactionsBetweenDates(@Param("startDate") LocalDate startDate, 
                                                      @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(pt.amount) FROM PostTransaction pt WHERE pt.folioNo = :folioNo")
    BigDecimal getTotalTransactionsByFolioNo(@Param("folioNo") String folioNo);
    
    @Query("SELECT SUM(pt.amount) FROM PostTransaction pt WHERE pt.billNo = :billNo")
    BigDecimal getTotalTransactionsByBillNo(@Param("billNo") String billNo);
    
    @Query("SELECT pt FROM PostTransaction pt WHERE pt.auditDate = :auditDate")
    List<PostTransaction> findByAuditDate(@Param("auditDate") LocalDate auditDate);
    
    @Query("SELECT SUM(pt.amount) FROM PostTransaction pt WHERE pt.accHeadId = :accHeadId AND pt.date = :date")
    BigDecimal getTotalByAccHeadAndDate(@Param("accHeadId") String accHeadId, @Param("date") LocalDate date);
    
    @Query("SELECT pt FROM PostTransaction pt WHERE pt.voucherNo = :voucherNo")
    List<PostTransaction> findByVoucherNo(@Param("voucherNo") String voucherNo);
}
