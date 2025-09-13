package com.hotelmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRegistrationRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "User type is required")
    private String userTypeId;

    private String hotelId;

    // Constructors
    public UserRegistrationRequest() {}

    public UserRegistrationRequest(String username, String password, String userTypeId, String hotelId) {
        this.username = username;
        this.password = password;
        this.userTypeId = userTypeId;
        this.hotelId = hotelId;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}
