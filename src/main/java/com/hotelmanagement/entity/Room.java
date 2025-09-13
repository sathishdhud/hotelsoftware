package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {

    @Id
    @Column(name = "room_id")
    private String roomId;

    @NotBlank(message = "Room number is required")
    @Column(name = "room_no", nullable = false)
    private String roomNo;

    @NotBlank(message = "Floor is required")
    @Column(name = "floor", nullable = false)
    private String floor;

    @NotBlank(message = "Status is required")
    @Column(name = "status", nullable = false)
    private String status; // VR, OD, OI, Blocked

    @Column(name = "room_type_id")
    private String roomTypeId;

    @Column(name = "additional_info")
    private String additionalInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", insertable = false, updatable = false)
    private RoomType roomType;

    // Constructors
    public Room() {}

    public Room(String roomId, String roomNo, String floor, String status) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.floor = floor;
        this.status = status;
    }

    // Getters and Setters
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(String roomTypeId) { this.roomTypeId = roomTypeId; }

    public String getAdditionalInfo() { return additionalInfo; }
    public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
}
