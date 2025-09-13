package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "nationality")
public class Nationality extends BaseEntity {

    @Id
    @Column(name = "id")
    private String id;

    @NotBlank(message = "Nationality is required")
    @Column(name = "nationality", nullable = false)
    private String nationality;

    // Constructors
    public Nationality() {}

    public Nationality(String id, String nationality) {
        this.id = id;
        this.nationality = nationality;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}
