package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "user_rights_data")
public class UserRightsData extends BaseEntity {

    @Id
    @Column(name = "id")
    private String id;

    @NotBlank(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotBlank(message = "Module name is required")
    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @NotBlank(message = "Permission type is required")
    @Column(name = "permission_type", nullable = false)
    private String permissionType; // read/write/none

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private HotelSoftUser hotelSoftUser;

    // Constructors
    public UserRightsData() {}

    public UserRightsData(String id, String userId, String moduleName, String permissionType) {
        this.id = id;
        this.userId = userId;
        this.moduleName = moduleName;
        this.permissionType = permissionType;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getModuleName() { return moduleName; }
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    public String getPermissionType() { return permissionType; }
    public void setPermissionType(String permissionType) { this.permissionType = permissionType; }

    public HotelSoftUser getHotelSoftUser() { return hotelSoftUser; }
    public void setHotelSoftUser(HotelSoftUser hotelSoftUser) { this.hotelSoftUser = hotelSoftUser; }
}
