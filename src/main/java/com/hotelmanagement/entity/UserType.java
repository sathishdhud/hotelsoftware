package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "user_type")
public class UserType extends BaseEntity {

    @Id
    @Column(name = "user_type_id")
    private String userTypeId;

    @NotBlank(message = "Type name is required")
    @Column(name = "type_name", nullable = false)
    private String typeName;

    // Constructors
    public UserType() {}

    public UserType(String userTypeId, String typeName) {
        this.userTypeId = userTypeId;
        this.typeName = typeName;
    }

    // Getters and Setters
    public String getUserTypeId() { return userTypeId; }
    public void setUserTypeId(String userTypeId) { this.userTypeId = userTypeId; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
}
