package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name = "fobill")
public class FoBill extends BaseEntity {

    @Id
    @Column(name = "bill_no")
    private String billNo;

    @NotBlank(message = "Folio number is required")
    @Column(name = "folio_no", nullable = false)
    private String folioNo;

    @NotBlank(message = "Guest name is required")
    @Column(name = "guest_name", nullable = false)
    private String guestName;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "advance_amount", precision = 10, scale = 2)
    private BigDecimal advanceAmount;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folio_no", insertable = false, updatable = false)
    private CheckIn checkIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private Room room;

    // Constructors
    public FoBill() {}

    // Getters and Setters
    public String getBillNo() { return billNo; }
    public void setBillNo(String billNo) { this.billNo = billNo; }

    public String getFolioNo() { return folioNo; }
    public void setFolioNo(String folioNo) { this.folioNo = folioNo; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getAdvanceAmount() { return advanceAmount; }
    public void setAdvanceAmount(BigDecimal advanceAmount) { this.advanceAmount = advanceAmount; }

    public CheckIn getCheckIn() { return checkIn; }
    public void setCheckIn(CheckIn checkIn) { this.checkIn = checkIn; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
}
