package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "arrival_mode")
public class ArrivalMode extends BaseEntity {

    @Id
    @Column(name = "id")
    private String id;

    @NotBlank(message = "Arrival mode is required")
    @Column(name = "arrival_mode", nullable = false)
    private String arrivalMode;

    // Constructors
    public ArrivalMode() {}

    public ArrivalMode(String id, String arrivalMode) {
        this.id = id;
        this.arrivalMode = arrivalMode;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getArrivalMode() { return arrivalMode; }
    public void setArrivalMode(String arrivalMode) { this.arrivalMode = arrivalMode; }
}
