package com.hotelmanagement.dto;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String userId;
    private String username;
    private String userTypeId;
    private String hotelId;

    public JwtAuthenticationResponse(String accessToken, String userId, String username, String userTypeId, String hotelId) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.userTypeId = userTypeId;
        this.hotelId = hotelId;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
