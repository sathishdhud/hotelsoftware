package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "shift")
public class Shift extends BaseEntity {

    @Id
    @Column(name = "shift_no")
    private String shiftNo;

    @NotNull(message = "Shift date is required")
    @Column(name = "shift_date", nullable = false)
    private LocalDate shiftDate;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    // Constructors
    public Shift() {}

    public Shift(String shiftNo, LocalDate shiftDate, BigDecimal balance) {
        this.shiftNo = shiftNo;
        this.shiftDate = shiftDate;
        this.balance = balance;
    }

    // Getters and Setters
    public String getShiftNo() { return shiftNo; }
    public void setShiftNo(String shiftNo) { this.shiftNo = shiftNo; }

    public LocalDate getShiftDate() { return shiftDate; }
    public void setShiftDate(LocalDate shiftDate) { this.shiftDate = shiftDate; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
