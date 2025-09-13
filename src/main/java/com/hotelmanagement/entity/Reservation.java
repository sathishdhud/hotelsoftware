package com.hotelmanagement.entity;

import com.hotelmanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @Id
    @Column(name = "reservation_no")
    private String reservationNo;

    @NotBlank(message = "Guest name is required")
    @Column(name = "guest_name", nullable = false)
    private String guestName;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "room_type_id")
    private String roomTypeId;

    @NotNull(message = "Arrival date is required")
    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @NotNull(message = "Departure date is required")
    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "no_of_days")
    private Integer noOfDays;

    @NotNull(message = "Number of persons is required")
    @Column(name = "no_of_persons", nullable = false)
    private Integer noOfPersons;

    @NotNull(message = "Number of rooms is required")
    @Column(name = "no_of_rooms", nullable = false)
    private Integer noOfRooms;

    @NotBlank(message = "Mobile number is required")
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "rate", precision = 10, scale = 2)
    private BigDecimal rate;

    @Column(name = "including_gst", length = 1)
    private String includingGst; // Y/N

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "rooms_checked_in")
    private Integer roomsCheckedIn = 0;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", insertable = false, updatable = false)
    private PlanType planType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", insertable = false, updatable = false)
    private RoomType roomType;

    // Constructors
    public Reservation() {}

    // Getters and Setters
    public String getReservationNo() { return reservationNo; }
    public void setReservationNo(String reservationNo) { this.reservationNo = reservationNo; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }

    public String getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(String roomTypeId) { this.roomTypeId = roomTypeId; }

    public LocalDate getArrivalDate() { return arrivalDate; }
    public void setArrivalDate(LocalDate arrivalDate) { this.arrivalDate = arrivalDate; }

    public LocalDate getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDate departureDate) { this.departureDate = departureDate; }

    public Integer getNoOfDays() { return noOfDays; }
    public void setNoOfDays(Integer noOfDays) { this.noOfDays = noOfDays; }

    public Integer getNoOfPersons() { return noOfPersons; }
    public void setNoOfPersons(Integer noOfPersons) { this.noOfPersons = noOfPersons; }

    public Integer getNoOfRooms() { return noOfRooms; }
    public void setNoOfRooms(Integer noOfRooms) { this.noOfRooms = noOfRooms; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }

    public String getIncludingGst() { return includingGst; }
    public void setIncludingGst(String includingGst) { this.includingGst = includingGst; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Integer getRoomsCheckedIn() { return roomsCheckedIn; }
    public void setRoomsCheckedIn(Integer roomsCheckedIn) { this.roomsCheckedIn = roomsCheckedIn; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public PlanType getPlanType() { return planType; }
    public void setPlanType(PlanType planType) { this.planType = planType; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
}
