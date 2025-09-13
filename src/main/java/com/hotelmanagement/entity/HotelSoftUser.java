package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "hotelsoftusers")
public class HotelSoftUser extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private String userId;

    @NotBlank(message = "Username is required")
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_type_id")
    private String userTypeId;

    @Column(name = "hotel_id")
    private String hotelId;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type_id", insertable = false, updatable = false)
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", insertable = false, updatable = false)
    private Hotel hotel;

    // Constructors
    public HotelSoftUser() {}

    public HotelSoftUser(String userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUserTypeId() { return userTypeId; }
    public void setUserTypeId(String userTypeId) { this.userTypeId = userTypeId; }

    public String getHotelId() { return hotelId; }
    public void setHotelId(String hotelId) { this.hotelId = hotelId; }

    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }
}
