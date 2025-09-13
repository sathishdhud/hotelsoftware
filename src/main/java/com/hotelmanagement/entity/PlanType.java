package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "plan_type")
public class PlanType extends BaseEntity {

    @Id
    @Column(name = "plan_id")
    private String planId;

    @NotBlank(message = "Plan name is required")
    @Column(name = "plan_name", nullable = false)
    private String planName;

    // Constructors
    public PlanType() {}

    public PlanType(String planId, String planName) {
        this.planId = planId;
        this.planName = planName;
    }

    // Getters and Setters
    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
}
