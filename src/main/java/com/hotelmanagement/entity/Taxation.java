package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "taxation")
public class Taxation extends BaseEntity {

    @Id
    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "tax_name")
    private String taxName;

    @Column(name = "percentage", precision = 5, scale = 2)
    private BigDecimal percentage;

    // Constructors
    public Taxation() {}

    public Taxation(String taxId, String taxName, BigDecimal percentage) {
        this.taxId = taxId;
        this.taxName = taxName;
        this.percentage = percentage;
    }

    // Getters and Setters
    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getTaxName() { return taxName; }
    public void setTaxName(String taxName) { this.taxName = taxName; }

    public BigDecimal getPercentage() { return percentage; }
    public void setPercentage(BigDecimal percentage) { this.percentage = percentage; }
}
