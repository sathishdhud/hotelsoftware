# Hotel Management System API Documentation

## Table of Contents
1. [Overview](#overview)
2. [Authentication](#authentication)
3. [Base Response Format](#base-response-format)
4. [Error Handling](#error-handling)
5. [API Endpoints](#api-endpoints)
   - [Authentication Controller](#authentication-controller)
   - [Reservation Controller](#reservation-controller)
   - [Check-In Controller](#check-in-controller)
   - [Room Controller](#room-controller)
   - [Billing Controller](#billing-controller)
   - [User Controller](#user-controller)
   - [Master Data Controller](#master-data-controller)
   - [Dashboard Controller](#dashboard-controller)
   - [Advance Controller](#advance-controller)
   - [Post Transaction Controller](#post-transaction-controller)
   - [Shift Controller](#shift-controller)
   - [Password Controller](#password-controller)

## Overview

The Hotel Management System API is a RESTful web service built with Spring Boot that provides comprehensive hotel operations management including reservations, check-ins, billing, user management, and reporting.

**Base URL:** `http://localhost:8080/api`

**Content-Type:** `application/json`

**Authentication:** JWT Bearer Token

## Authentication

All protected endpoints require a JWT token in the Authorization header:

\`\`\`
Authorization: Bearer <jwt_token>
\`\`\`

## Base Response Format

All API responses follow a consistent format using the `ApiResponse<T>` wrapper:

\`\`\`json
{
  "success": true,
  "message": "Operation successful",
  "data": { /* response data */ },
  "error": null
}
\`\`\`

**Success Response:**
\`\`\`json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": { /* actual response data */ }
}
\`\`\`

**Error Response:**
\`\`\`json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "error": "Detailed error message"
}
\`\`\`

## Error Handling

The API uses standard HTTP status codes:

- `200 OK` - Successful GET, PUT, PATCH requests
- `201 Created` - Successful POST requests
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## API Endpoints

---

## Authentication Controller

Base path: `/auth`

### POST /auth/login
Authenticate user and get JWT token.

**Request:**
\`\`\`json
{
  "username": "admin",
  "password": "admin123"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User authenticated successfully",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer",
    "userId": "USR001",
    "username": "admin",
    "userTypeId": "UT001",
    "hotelId": "HTL001"
  }
}
\`\`\`

### POST /auth/register
Register a new user.

**Request:**
\`\`\`json
{
  "username": "newuser",
  "password": "password123",
  "userTypeId": "UT002",
  "hotelId": "HTL001"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "userId": "USR002",
    "userName": "newuser",
    "userTypeId": "UT002",
    "hotelId": "HTL001",
    "createdDate": "2024-01-15T10:30:00",
    "isActive": true
  }
}
\`\`\`

### GET /auth/me
Get current authenticated user information.

**Headers:**
\`\`\`
Authorization: Bearer <jwt_token>
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Current user retrieved",
  "data": {
    "userId": "USR001",
    "username": "admin",
    "userTypeId": "UT001",
    "hotelId": "HTL001",
    "authorities": ["ROLE_ADMIN"]
  }
}
\`\`\`

### POST /auth/refresh
Refresh JWT token.

**Headers:**
\`\`\`
Authorization: Bearer <jwt_token>
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Token refreshed successfully",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer",
    "userId": "USR001",
    "username": "admin",
    "userTypeId": "UT001",
    "hotelId": "HTL001"
  }
}
\`\`\`

---

## Reservation Controller

Base path: `/reservations`

### GET /reservations
Get all reservations.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservations retrieved successfully",
  "data": [
    {
      "reservationNo": "RES001",
      "guestName": "John Doe",
      "mobileNumber": "+1234567890",
      "emailId": "john.doe@email.com",
      "arrivalDate": "2024-01-20",
      "departureDate": "2024-01-25",
      "noOfRooms": 2,
      "noOfAdults": 2,
      "noOfChildren": 1,
      "roomTypeId": "RT001",
      "planTypeId": "PT001",
      "roomRate": 150.00,
      "totalAmount": 750.00,
      "advanceAmount": 200.00,
      "reservationStatus": "CONFIRMED",
      "roomsCheckedIn": 0,
      "createdDate": "2024-01-15T10:30:00"
    }
  ]
}
\`\`\`

### GET /reservations/{reservationNo}
Get reservation by reservation number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservation retrieved successfully",
  "data": {
    "reservationNo": "RES001",
    "guestName": "John Doe",
    "mobileNumber": "+1234567890",
    "emailId": "john.doe@email.com",
    "arrivalDate": "2024-01-20",
    "departureDate": "2024-01-25",
    "noOfRooms": 2,
    "noOfAdults": 2,
    "noOfChildren": 1,
    "roomTypeId": "RT001",
    "planTypeId": "PT001",
    "roomRate": 150.00,
    "totalAmount": 750.00,
    "advanceAmount": 200.00,
    "reservationStatus": "CONFIRMED",
    "roomsCheckedIn": 0,
    "createdDate": "2024-01-15T10:30:00"
  }
}
\`\`\`

### GET /reservations/search/guest?guestName={name}
Search reservations by guest name.

**Query Parameters:**
- `guestName` (string, required): Guest name to search

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservations found",
  "data": [
    {
      "reservationNo": "RES001",
      "guestName": "John Doe",
      "mobileNumber": "+1234567890",
      "arrivalDate": "2024-01-20",
      "departureDate": "2024-01-25",
      "reservationStatus": "CONFIRMED"
    }
  ]
}
\`\`\`

### GET /reservations/search/mobile?mobileNumber={number}
Search reservations by mobile number.

**Query Parameters:**
- `mobileNumber` (string, required): Mobile number to search

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservations found",
  "data": [
    {
      "reservationNo": "RES001",
      "guestName": "John Doe",
      "mobileNumber": "+1234567890",
      "arrivalDate": "2024-01-20",
      "departureDate": "2024-01-25",
      "reservationStatus": "CONFIRMED"
    }
  ]
}
\`\`\`

### GET /reservations/arrivals/{date}
Get expected arrivals for a specific date.

**Path Parameters:**
- `date` (string, required): Date in YYYY-MM-DD format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Expected arrivals retrieved",
  "data": [
    {
      "reservationNo": "RES001",
      "guestName": "John Doe",
      "arrivalDate": "2024-01-20",
      "noOfRooms": 2,
      "roomTypeId": "RT001",
      "reservationStatus": "CONFIRMED"
    }
  ]
}
\`\`\`

### GET /reservations/departures/{date}
Get expected departures for a specific date.

**Path Parameters:**
- `date` (string, required): Date in YYYY-MM-DD format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Expected departures retrieved",
  "data": [
    {
      "reservationNo": "RES002",
      "guestName": "Jane Smith",
      "departureDate": "2024-01-20",
      "noOfRooms": 1,
      "roomTypeId": "RT002",
      "reservationStatus": "CONFIRMED"
    }
  ]
}
\`\`\`

### GET /reservations/pending-checkins
Get reservations pending check-in.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Pending check-ins retrieved",
  "data": [
    {
      "reservationNo": "RES003",
      "guestName": "Bob Johnson",
      "arrivalDate": "2024-01-19",
      "noOfRooms": 1,
      "roomsCheckedIn": 0,
      "reservationStatus": "CONFIRMED"
    }
  ]
}
\`\`\`

### POST /reservations
Create a new reservation.

**Request:**
\`\`\`json
{
  "guestName": "Alice Brown",
  "mobileNumber": "+1987654321",
  "emailId": "alice.brown@email.com",
  "arrivalDate": "2024-01-22",
  "departureDate": "2024-01-26",
  "noOfRooms": 1,
  "noOfAdults": 2,
  "noOfChildren": 0,
  "roomTypeId": "RT001",
  "planTypeId": "PT001",
  "roomRate": 150.00,
  "advanceAmount": 100.00,
  "specialRequests": "Late check-in",
  "companyId": "COMP001",
  "nationalityId": "NAT001",
  "arrivalModeId": "AM001",
  "resvSourceId": "RS001"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservation created successfully",
  "data": {
    "reservationNo": "RES004",
    "guestName": "Alice Brown",
    "mobileNumber": "+1987654321",
    "emailId": "alice.brown@email.com",
    "arrivalDate": "2024-01-22",
    "departureDate": "2024-01-26",
    "noOfRooms": 1,
    "noOfAdults": 2,
    "noOfChildren": 0,
    "roomTypeId": "RT001",
    "planTypeId": "PT001",
    "roomRate": 150.00,
    "totalAmount": 600.00,
    "advanceAmount": 100.00,
    "reservationStatus": "CONFIRMED",
    "roomsCheckedIn": 0,
    "createdDate": "2024-01-15T14:30:00"
  }
}
\`\`\`

### PUT /reservations/{reservationNo}
Update an existing reservation.

**Request:**
\`\`\`json
{
  "guestName": "Alice Brown Updated",
  "mobileNumber": "+1987654321",
  "emailId": "alice.brown.updated@email.com",
  "arrivalDate": "2024-01-22",
  "departureDate": "2024-01-27",
  "noOfRooms": 2,
  "noOfAdults": 3,
  "noOfChildren": 1,
  "roomRate": 160.00,
  "advanceAmount": 150.00
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservation updated successfully",
  "data": {
    "reservationNo": "RES004",
    "guestName": "Alice Brown Updated",
    "mobileNumber": "+1987654321",
    "emailId": "alice.brown.updated@email.com",
    "arrivalDate": "2024-01-22",
    "departureDate": "2024-01-27",
    "noOfRooms": 2,
    "noOfAdults": 3,
    "noOfChildren": 1,
    "roomRate": 160.00,
    "totalAmount": 800.00,
    "advanceAmount": 150.00,
    "reservationStatus": "CONFIRMED",
    "modifiedDate": "2024-01-15T15:45:00"
  }
}
\`\`\`

### DELETE /reservations/{reservationNo}
Delete a reservation.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservation deleted successfully"
}
\`\`\`

### POST /reservations/{reservationNo}/checkin-room
Increment rooms checked in for a reservation.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room checked in successfully"
}
\`\`\`

### GET /reservations/{reservationNo}/can-checkin
Check if more rooms can be checked in for a reservation.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Check-in availability checked",
  "data": true
}
\`\`\`

---

## Check-In Controller

Base path: `/checkins`

### GET /checkins
Get all check-ins.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Check-ins retrieved successfully",
  "data": [
    {
      "folioNo": "FOL001",
      "reservationNo": "RES001",
      "guestName": "John Doe",
      "mobileNumber": "+1234567890",
      "roomId": "RM101",
      "checkInDate": "2024-01-20T15:00:00",
      "checkOutDate": "2024-01-25T11:00:00",
      "actualCheckOutDate": null,
      "noOfAdults": 2,
      "noOfChildren": 1,
      "planTypeId": "PT001",
      "roomRate": 150.00,
      "folioStatus": "INHOUSE",
      "isWalkIn": false,
      "createdDate": "2024-01-20T15:00:00"
    }
  ]
}
\`\`\`

### GET /checkins/{folioNo}
Get check-in by folio number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Check-in retrieved successfully",
  "data": {
    "folioNo": "FOL001",
    "reservationNo": "RES001",
    "guestName": "John Doe",
    "mobileNumber": "+1234567890",
    "roomId": "RM101",
    "checkInDate": "2024-01-20T15:00:00",
    "checkOutDate": "2024-01-25T11:00:00",
    "actualCheckOutDate": null,
    "noOfAdults": 2,
    "noOfChildren": 1,
    "planTypeId": "PT001",
    "roomRate": 150.00,
    "folioStatus": "INHOUSE",
    "isWalkIn": false,
    "createdDate": "2024-01-20T15:00:00"
  }
}
\`\`\`

### GET /checkins/search/guest?guestName={name}
Search check-ins by guest name.

**Query Parameters:**
- `guestName` (string, required): Guest name to search

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Check-ins found",
  "data": [
    {
      "folioNo": "FOL001",
      "guestName": "John Doe",
      "roomId": "RM101",
      "checkInDate": "2024-01-20T15:00:00",
      "folioStatus": "INHOUSE"
    }
  ]
}
\`\`\`

### GET /checkins/inhouse
Get all in-house guests.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "In-house guests retrieved",
  "data": [
    {
      "folioNo": "FOL001",
      "guestName": "John Doe",
      "roomId": "RM101",
      "checkInDate": "2024-01-20T15:00:00",
      "checkOutDate": "2024-01-25T11:00:00",
      "folioStatus": "INHOUSE"
    }
  ]
}
\`\`\`

### GET /checkins/checkouts/{date}
Get check-outs for a specific date.

**Path Parameters:**
- `date` (string, required): Date in YYYY-MM-DD format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Check-outs for date retrieved",
  "data": [
    {
      "folioNo": "FOL002",
      "guestName": "Jane Smith",
      "roomId": "RM102",
      "checkOutDate": "2024-01-20T11:00:00",
      "actualCheckOutDate": "2024-01-20T10:30:00",
      "folioStatus": "CHECKEDOUT"
    }
  ]
}
\`\`\`

### GET /checkins/walkins
Get all walk-in guests.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Walk-in guests retrieved",
  "data": [
    {
      "folioNo": "FOL003",
      "guestName": "Bob Wilson",
      "roomId": "RM103",
      "checkInDate": "2024-01-20T18:00:00",
      "isWalkIn": true,
      "folioStatus": "INHOUSE"
    }
  ]
}
\`\`\`

### POST /checkins/with-reservation?reservationNo={reservationNo}&roomId={roomId}
Check-in a guest with existing reservation.

**Query Parameters:**
- `reservationNo` (string, required): Reservation number
- `roomId` (string, required): Room ID to assign

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Guest checked in successfully",
  "data": {
    "folioNo": "FOL004",
    "reservationNo": "RES001",
    "guestName": "John Doe",
    "roomId": "RM104",
    "checkInDate": "2024-01-20T15:30:00",
    "checkOutDate": "2024-01-25T11:00:00",
    "folioStatus": "INHOUSE",
    "isWalkIn": false
  }
}
\`\`\`

### POST /checkins/walkin?roomId={roomId}
Walk-in check-in for a guest without reservation.

**Query Parameters:**
- `roomId` (string, required): Room ID to assign

**Request:**
\`\`\`json
{
  "guestName": "Walk-in Guest",
  "mobileNumber": "+1555666777",
  "emailId": "walkin@email.com",
  "checkInDate": "2024-01-20T20:00:00",
  "checkOutDate": "2024-01-22T11:00:00",
  "noOfAdults": 1,
  "noOfChildren": 0,
  "planTypeId": "PT001",
  "roomRate": 120.00,
  "nationalityId": "NAT001",
  "arrivalModeId": "AM002"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Walk-in guest checked in successfully",
  "data": {
    "folioNo": "FOL005",
    "reservationNo": null,
    "guestName": "Walk-in Guest",
    "mobileNumber": "+1555666777",
    "roomId": "RM105",
    "checkInDate": "2024-01-20T20:00:00",
    "checkOutDate": "2024-01-22T11:00:00",
    "folioStatus": "INHOUSE",
    "isWalkIn": true
  }
}
\`\`\`

### PUT /checkins/{folioNo}
Update check-in information.

**Request:**
\`\`\`json
{
  "guestName": "Updated Guest Name",
  "mobileNumber": "+1555666888",
  "checkOutDate": "2024-01-23T11:00:00",
  "noOfAdults": 2,
  "roomRate": 130.00
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Check-in updated successfully",
  "data": {
    "folioNo": "FOL005",
    "guestName": "Updated Guest Name",
    "mobileNumber": "+1555666888",
    "checkOutDate": "2024-01-23T11:00:00",
    "noOfAdults": 2,
    "roomRate": 130.00,
    "modifiedDate": "2024-01-20T21:00:00"
  }
}
\`\`\`

### GET /checkins/count/inhouse
Get count of in-house guests.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "In-house guest count retrieved",
  "data": 15
}
\`\`\`

---

## Room Controller

Base path: `/rooms`

### GET /rooms
Get all rooms.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Rooms retrieved successfully",
  "data": [
    {
      "roomId": "RM101",
      "roomNo": "101",
      "roomTypeId": "RT001",
      "floor": "1",
      "roomStatus": "VACANT",
      "isActive": true,
      "maxOccupancy": 2,
      "bedType": "DOUBLE",
      "amenities": "AC, TV, WiFi",
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

### GET /rooms/{roomId}
Get room by ID.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room retrieved successfully",
  "data": {
    "roomId": "RM101",
    "roomNo": "101",
    "roomTypeId": "RT001",
    "floor": "1",
    "roomStatus": "VACANT",
    "isActive": true,
    "maxOccupancy": 2,
    "bedType": "DOUBLE",
    "amenities": "AC, TV, WiFi",
    "createdDate": "2024-01-01T00:00:00"
  }
}
\`\`\`

### GET /rooms/by-number/{roomNo}
Get room by room number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room retrieved successfully",
  "data": {
    "roomId": "RM101",
    "roomNo": "101",
    "roomTypeId": "RT001",
    "floor": "1",
    "roomStatus": "VACANT",
    "isActive": true
  }
}
\`\`\`

### GET /rooms/by-status/{status}
Get rooms by status.

**Path Parameters:**
- `status` (string, required): Room status (VACANT, OCCUPIED, BLOCKED, MAINTENANCE)

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Rooms retrieved by status",
  "data": [
    {
      "roomId": "RM101",
      "roomNo": "101",
      "roomStatus": "VACANT",
      "roomTypeId": "RT001",
      "floor": "1"
    },
    {
      "roomId": "RM102",
      "roomNo": "102",
      "roomStatus": "VACANT",
      "roomTypeId": "RT001",
      "floor": "1"
    }
  ]
}
\`\`\`

### GET /rooms/vacant
Get all vacant rooms.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Vacant rooms retrieved",
  "data": [
    {
      "roomId": "RM101",
      "roomNo": "101",
      "roomTypeId": "RT001",
      "floor": "1",
      "roomStatus": "VACANT"
    }
  ]
}
\`\`\`

### GET /rooms/occupied
Get all occupied rooms.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Occupied rooms retrieved",
  "data": [
    {
      "roomId": "RM201",
      "roomNo": "201",
      "roomTypeId": "RT002",
      "floor": "2",
      "roomStatus": "OCCUPIED"
    }
  ]
}
\`\`\`

### GET /rooms/blocked
Get all blocked rooms.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Blocked rooms retrieved",
  "data": [
    {
      "roomId": "RM301",
      "roomNo": "301",
      "roomTypeId": "RT001",
      "floor": "3",
      "roomStatus": "BLOCKED"
    }
  ]
}
\`\`\`

### GET /rooms/by-floor/{floor}
Get rooms by floor.

**Path Parameters:**
- `floor` (string, required): Floor number

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Rooms retrieved by floor",
  "data": [
    {
      "roomId": "RM101",
      "roomNo": "101",
      "floor": "1",
      "roomStatus": "VACANT"
    },
    {
      "roomId": "RM102",
      "roomNo": "102",
      "floor": "1",
      "roomStatus": "OCCUPIED"
    }
  ]
}
\`\`\`

### GET /rooms/by-type/{roomTypeId}
Get rooms by room type.

**Path Parameters:**
- `roomTypeId` (string, required): Room type ID

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Rooms retrieved by type",
  "data": [
    {
      "roomId": "RM101",
      "roomNo": "101",
      "roomTypeId": "RT001",
      "roomStatus": "VACANT"
    }
  ]
}
\`\`\`

### POST /rooms
Create a new room.

**Request:**
\`\`\`json
{
  "roomNo": "401",
  "roomTypeId": "RT001",
  "floor": "4",
  "maxOccupancy": 2,
  "bedType": "DOUBLE",
  "amenities": "AC, TV, WiFi, Minibar",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room created successfully",
  "data": {
    "roomId": "RM401",
    "roomNo": "401",
    "roomTypeId": "RT001",
    "floor": "4",
    "roomStatus": "VACANT",
    "maxOccupancy": 2,
    "bedType": "DOUBLE",
    "amenities": "AC, TV, WiFi, Minibar",
    "isActive": true,
    "createdDate": "2024-01-15T16:00:00"
  }
}
\`\`\`

### PUT /rooms/{roomId}
Update room information.

**Request:**
\`\`\`json
{
  "roomNo": "401A",
  "maxOccupancy": 3,
  "amenities": "AC, TV, WiFi, Minibar, Balcony",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room updated successfully",
  "data": {
    "roomId": "RM401",
    "roomNo": "401A",
    "roomTypeId": "RT001",
    "floor": "4",
    "maxOccupancy": 3,
    "amenities": "AC, TV, WiFi, Minibar, Balcony",
    "modifiedDate": "2024-01-15T16:30:00"
  }
}
\`\`\`

### DELETE /rooms/{roomId}
Delete a room.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room deleted successfully"
}
\`\`\`

### PATCH /rooms/{roomId}/status?status={status}
Update room status.

**Query Parameters:**
- `status` (string, required): New room status

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room status updated successfully"
}
\`\`\`

### GET /rooms/count/vacant
Get count of vacant rooms.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Vacant room count retrieved",
  "data": 25
}
\`\`\`

### GET /rooms/count/occupied
Get count of occupied rooms.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Occupied room count retrieved",
  "data": 15
}
\`\`\`

---

## Billing Controller

Base path: `/billing`

### GET /billing/bills
Get all bills.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Bills retrieved successfully",
  "data": [
    {
      "billNo": "BILL001",
      "folioNo": "FOL001",
      "guestName": "John Doe",
      "roomNo": "101",
      "checkInDate": "2024-01-20T15:00:00",
      "checkOutDate": "2024-01-25T11:00:00",
      "totalAmount": 750.00,
      "taxAmount": 135.00,
      "discountAmount": 0.00,
      "netAmount": 885.00,
      "paidAmount": 200.00,
      "balanceAmount": 685.00,
      "billStatus": "PENDING",
      "billDate": "2024-01-25T11:30:00",
      "createdDate": "2024-01-25T11:30:00"
    }
  ]
}
\`\`\`

### GET /billing/bills/{billNo}
Get bill by bill number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Bill retrieved successfully",
  "data": {
    "billNo": "BILL001",
    "folioNo": "FOL001",
    "guestName": "John Doe",
    "roomNo": "101",
    "checkInDate": "2024-01-20T15:00:00",
    "checkOutDate": "2024-01-25T11:00:00",
    "totalAmount": 750.00,
    "taxAmount": 135.00,
    "discountAmount": 0.00,
    "netAmount": 885.00,
    "paidAmount": 200.00,
    "balanceAmount": 685.00,
    "billStatus": "PENDING",
    "billDate": "2024-01-25T11:30:00"
  }
}
\`\`\`

### GET /billing/bills/by-folio/{folioNo}
Get bill by folio number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Bill retrieved by folio number",
  "data": {
    "billNo": "BILL001",
    "folioNo": "FOL001",
    "guestName": "John Doe",
    "totalAmount": 750.00,
    "netAmount": 885.00,
    "billStatus": "PENDING"
  }
}
\`\`\`

### GET /billing/bills/search/guest?guestName={name}
Search bills by guest name.

**Query Parameters:**
- `guestName` (string, required): Guest name to search

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Bills found by guest name",
  "data": [
    {
      "billNo": "BILL001",
      "folioNo": "FOL001",
      "guestName": "John Doe",
      "totalAmount": 750.00,
      "billStatus": "PENDING"
    }
  ]
}
\`\`\`

### POST /billing/generate/{folioNo}
Generate bill for a folio.

**Path Parameters:**
- `folioNo` (string, required): Folio number

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Bill generated successfully",
  "data": {
    "billNo": "BILL002",
    "folioNo": "FOL002",
    "guestName": "Jane Smith",
    "totalAmount": 600.00,
    "taxAmount": 108.00,
    "netAmount": 708.00,
    "paidAmount": 0.00,
    "balanceAmount": 708.00,
    "billStatus": "PENDING",
    "billDate": "2024-01-25T12:00:00"
  }
}
\`\`\`

### POST /billing/split/{originalBillNo}
Create a split bill.

**Path Parameters:**
- `originalBillNo` (string, required): Original bill number

**Request:**
\`\`\`json
[
  "TXN001",
  "TXN002",
  "TXN003"
]
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Split bill created successfully",
  "data": {
    "billNo": "BILL003",
    "originalBillNo": "BILL001",
    "folioNo": "FOL001",
    "guestName": "John Doe",
    "totalAmount": 300.00,
    "taxAmount": 54.00,
    "netAmount": 354.00,
    "billStatus": "SPLIT",
    "billDate": "2024-01-25T12:30:00"
  }
}
\`\`\`

### GET /billing/total/by-date/{date}
Get total bill amount for a specific date.

**Path Parameters:**
- `date` (string, required): Date in YYYY-MM-DDTHH:mm:ss format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Total bill amount calculated",
  "data": 2500.00
}
\`\`\`

### GET /billing/count/by-date/{date}
Get bill count for a specific date.

**Path Parameters:**
- `date` (string, required): Date in YYYY-MM-DDTHH:mm:ss format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Bill count retrieved",
  "data": 5
}
\`\`\`

### DELETE /billing/bills/{billNo}
Delete a bill.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Bill deleted successfully"
}
\`\`\`

---

## User Controller

Base path: `/users`

### GET /users
Get all users.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Users retrieved successfully",
  "data": [
    {
      "userId": "USR001",
      "userName": "admin",
      "userTypeId": "UT001",
      "hotelId": "HTL001",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00",
      "lastLoginDate": "2024-01-15T10:30:00"
    }
  ]
}
\`\`\`

### GET /users/{userId}
Get user by ID.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User retrieved successfully",
  "data": {
    "userId": "USR001",
    "userName": "admin",
    "userTypeId": "UT001",
    "hotelId": "HTL001",
    "isActive": true,
    "createdDate": "2024-01-01T00:00:00",
    "lastLoginDate": "2024-01-15T10:30:00"
  }
}
\`\`\`

### GET /users/by-username/{username}
Get user by username.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User retrieved successfully",
  "data": {
    "userId": "USR001",
    "userName": "admin",
    "userTypeId": "UT001",
    "hotelId": "HTL001",
    "isActive": true
  }
}
\`\`\`

### GET /users/by-hotel/{hotelId}
Get users by hotel.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Users retrieved by hotel",
  "data": [
    {
      "userId": "USR001",
      "userName": "admin",
      "userTypeId": "UT001",
      "hotelId": "HTL001",
      "isActive": true
    }
  ]
}
\`\`\`

### GET /users/by-type/{userTypeId}
Get users by user type.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Users retrieved by type",
  "data": [
    {
      "userId": "USR001",
      "userName": "admin",
      "userTypeId": "UT001",
      "isActive": true
    }
  ]
}
\`\`\`

### POST /users
Create a new user.

**Request:**
\`\`\`json
{
  "userName": "frontdesk",
  "password": "password123",
  "userTypeId": "UT002",
  "hotelId": "HTL001",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "userId": "USR002",
    "userName": "frontdesk",
    "userTypeId": "UT002",
    "hotelId": "HTL001",
    "isActive": true,
    "createdDate": "2024-01-15T17:00:00"
  }
}
\`\`\`

### PUT /users/{userId}
Update user information.

**Request:**
\`\`\`json
{
  "userName": "frontdesk_updated",
  "userTypeId": "UT002",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User updated successfully",
  "data": {
    "userId": "USR002",
    "userName": "frontdesk_updated",
    "userTypeId": "UT002",
    "hotelId": "HTL001",
    "isActive": true,
    "modifiedDate": "2024-01-15T17:30:00"
  }
}
\`\`\`

### DELETE /users/{userId}
Delete a user.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User deleted successfully"
}
\`\`\`

### GET /users/{userId}/rights
Get user rights.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User rights retrieved",
  "data": [
    {
      "userRightId": "UR001",
      "userId": "USR001",
      "moduleName": "RESERVATIONS",
      "permissionType": "WRITE",
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "userRightId": "UR002",
      "userId": "USR001",
      "moduleName": "CHECKINS",
      "permissionType": "WRITE",
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

### GET /users/{userId}/modules
Get user accessible modules.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Accessible modules retrieved",
  "data": [
    "RESERVATIONS",
    "CHECKINS",
    "BILLING",
    "ROOMS"
  ]
}
\`\`\`

### POST /users/{userId}/rights?moduleName={moduleName}&permissionType={permissionType}
Set user right for a module.

**Query Parameters:**
- `moduleName` (string, required): Module name
- `permissionType` (string, required): Permission type (READ, WRITE, NONE)

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User right set successfully",
  "data": {
    "userRightId": "UR003",
    "userId": "USR002",
    "moduleName": "RESERVATIONS",
    "permissionType": "READ",
    "createdDate": "2024-01-15T18:00:00"
  }
}
\`\`\`

### GET /users/{userId}/permissions/{moduleName}?permissionType={permissionType}
Check user permission for a module.

**Query Parameters:**
- `permissionType` (string, required): Permission type to check

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Permission checked",
  "data": true
}
\`\`\`

### GET /users/{userId}/can-read/{moduleName}
Check if user can read a module.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Read permission checked",
  "data": true
}
\`\`\`

### GET /users/{userId}/can-write/{moduleName}
Check if user can write to a module.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Write permission checked",
  "data": false
}
\`\`\`

---

## Master Data Controller

Base path: `/master-data`

### Room Types

#### GET /master-data/room-types
Get all room types.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room types retrieved successfully",
  "data": [
    {
      "roomTypeId": "RT001",
      "roomTypeName": "Standard Room",
      "roomTypeDesc": "Standard room with basic amenities",
      "baseRate": 100.00,
      "maxOccupancy": 2,
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "roomTypeId": "RT002",
      "roomTypeName": "Deluxe Room",
      "roomTypeDesc": "Deluxe room with premium amenities",
      "baseRate": 150.00,
      "maxOccupancy": 3,
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/room-types
Create a new room type.

**Request:**
\`\`\`json
{
  "roomTypeName": "Suite Room",
  "roomTypeDesc": "Luxury suite with separate living area",
  "baseRate": 250.00,
  "maxOccupancy": 4,
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room type created successfully",
  "data": {
    "roomTypeId": "RT003",
    "roomTypeName": "Suite Room",
    "roomTypeDesc": "Luxury suite with separate living area",
    "baseRate": 250.00,
    "maxOccupancy": 4,
    "isActive": true,
    "createdDate": "2024-01-15T18:30:00"
  }
}
\`\`\`

#### PUT /master-data/room-types/{typeId}
Update room type.

**Request:**
\`\`\`json
{
  "roomTypeName": "Premium Suite",
  "roomTypeDesc": "Premium luxury suite with separate living area",
  "baseRate": 300.00,
  "maxOccupancy": 4,
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room type updated successfully",
  "data": {
    "roomTypeId": "RT003",
    "roomTypeName": "Premium Suite",
    "roomTypeDesc": "Premium luxury suite with separate living area",
    "baseRate": 300.00,
    "maxOccupancy": 4,
    "isActive": true,
    "modifiedDate": "2024-01-15T19:00:00"
  }
}
\`\`\`

### Companies

#### GET /master-data/companies
Get all companies.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Companies retrieved successfully",
  "data": [
    {
      "companyId": "COMP001",
      "companyName": "ABC Corporation",
      "contactPerson": "John Manager",
      "phoneNumber": "+1234567890",
      "emailId": "contact@abc.com",
      "address": "123 Business St, City",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/companies
Create a new company.

**Request:**
\`\`\`json
{
  "companyName": "XYZ Enterprises",
  "contactPerson": "Jane Director",
  "phoneNumber": "+1987654321",
  "emailId": "info@xyz.com",
  "address": "456 Corporate Ave, City",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Company created successfully",
  "data": {
    "companyId": "COMP002",
    "companyName": "XYZ Enterprises",
    "contactPerson": "Jane Director",
    "phoneNumber": "+1987654321",
    "emailId": "info@xyz.com",
    "address": "456 Corporate Ave, City",
    "isActive": true,
    "createdDate": "2024-01-15T19:30:00"
  }
}
\`\`\`

### Plan Types

#### GET /master-data/plan-types
Get all plan types.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Plan types retrieved successfully",
  "data": [
    {
      "planTypeId": "PT001",
      "planTypeName": "Room Only",
      "planTypeDesc": "Room accommodation only",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "planTypeId": "PT002",
      "planTypeName": "Bed & Breakfast",
      "planTypeDesc": "Room with complimentary breakfast",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/plan-types
Create a new plan type.

**Request:**
\`\`\`json
{
  "planTypeName": "Full Board",
  "planTypeDesc": "Room with all meals included",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Plan type created successfully",
  "data": {
    "planTypeId": "PT003",
    "planTypeName": "Full Board",
    "planTypeDesc": "Room with all meals included",
    "isActive": true,
    "createdDate": "2024-01-15T20:00:00"
  }
}
\`\`\`

### Settlement Types

#### GET /master-data/settlement-types
Get all bill settlement types.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Settlement types retrieved successfully",
  "data": [
    {
      "settlementTypeId": "ST001",
      "settlementTypeName": "Cash",
      "settlementTypeDesc": "Cash payment",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "settlementTypeId": "ST002",
      "settlementTypeName": "Credit Card",
      "settlementTypeDesc": "Credit card payment",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/settlement-types
Create a new settlement type.

**Request:**
\`\`\`json
{
  "settlementTypeName": "Bank Transfer",
  "settlementTypeDesc": "Electronic bank transfer",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Settlement type created successfully",
  "data": {
    "settlementTypeId": "ST003",
    "settlementTypeName": "Bank Transfer",
    "settlementTypeDesc": "Electronic bank transfer",
    "isActive": true,
    "createdDate": "2024-01-15T20:30:00"
  }
}
\`\`\`

### Account Heads

#### GET /master-data/account-heads
Get all account heads.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Account heads retrieved successfully",
  "data": [
    {
      "accountHeadId": "AH001",
      "accountHeadName": "Room Revenue",
      "accountHeadDesc": "Revenue from room bookings",
      "accountType": "REVENUE",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/account-heads
Create a new account head.

**Request:**
\`\`\`json
{
  "accountHeadName": "Food & Beverage",
  "accountHeadDesc": "Revenue from restaurant and bar",
  "accountType": "REVENUE",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Account head created successfully",
  "data": {
    "accountHeadId": "AH002",
    "accountHeadName": "Food & Beverage",
    "accountHeadDesc": "Revenue from restaurant and bar",
    "accountType": "REVENUE",
    "isActive": true,
    "createdDate": "2024-01-15T21:00:00"
  }
}
\`\`\`

### Taxes

#### GET /master-data/taxes
Get all taxes.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Taxes retrieved successfully",
  "data": [
    {
      "taxId": "TAX001",
      "taxName": "GST",
      "taxDesc": "Goods and Services Tax",
      "taxRate": 18.00,
      "taxType": "PERCENTAGE",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/taxes
Create a new tax.

**Request:**
\`\`\`json
{
  "taxName": "Service Tax",
  "taxDesc": "Service tax on hospitality",
  "taxRate": 10.00,
  "taxType": "PERCENTAGE",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Tax created successfully",
  "data": {
    "taxId": "TAX002",
    "taxName": "Service Tax",
    "taxDesc": "Service tax on hospitality",
    "taxRate": 10.00,
    "taxType": "PERCENTAGE",
    "isActive": true,
    "createdDate": "2024-01-15T21:30:00"
  }
}
\`\`\`

### User Types

#### GET /master-data/user-types
Get all user types.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User types retrieved successfully",
  "data": [
    {
      "userTypeId": "UT001",
      "userTypeName": "Administrator",
      "userTypeDesc": "System administrator with full access",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "userTypeId": "UT002",
      "userTypeName": "Front Desk",
      "userTypeDesc": "Front desk staff with limited access",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/user-types
Create a new user type.

**Request:**
\`\`\`json
{
  "userTypeName": "Manager",
  "userTypeDesc": "Hotel manager with supervisory access",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "User type created successfully",
  "data": {
    "userTypeId": "UT003",
    "userTypeName": "Manager",
    "userTypeDesc": "Hotel manager with supervisory access",
    "isActive": true,
    "createdDate": "2024-01-15T22:00:00"
  }
}
\`\`\`

### Nationalities

#### GET /master-data/nationalities
Get all nationalities.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Nationalities retrieved successfully",
  "data": [
    {
      "nationalityId": "NAT001",
      "nationalityName": "American",
      "countryCode": "US",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "nationalityId": "NAT002",
      "nationalityName": "Indian",
      "countryCode": "IN",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/nationalities
Create a new nationality.

**Request:**
\`\`\`json
{
  "nationalityName": "British",
  "countryCode": "GB",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Nationality created successfully",
  "data": {
    "nationalityId": "NAT003",
    "nationalityName": "British",
    "countryCode": "GB",
    "isActive": true,
    "createdDate": "2024-01-15T22:30:00"
  }
}
\`\`\`

### Arrival Modes

#### GET /master-data/arrival-modes
Get all arrival modes.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Arrival modes retrieved successfully",
  "data": [
    {
      "arrivalModeId": "AM001",
      "arrivalModeName": "Flight",
      "arrivalModeDesc": "Arrival by flight",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "arrivalModeId": "AM002",
      "arrivalModeName": "Car",
      "arrivalModeDesc": "Arrival by car",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/arrival-modes
Create a new arrival mode.

**Request:**
\`\`\`json
{
  "arrivalModeName": "Train",
  "arrivalModeDesc": "Arrival by train",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Arrival mode created successfully",
  "data": {
    "arrivalModeId": "AM003",
    "arrivalModeName": "Train",
    "arrivalModeDesc": "Arrival by train",
    "isActive": true,
    "createdDate": "2024-01-15T23:00:00"
  }
}
\`\`\`

### Reservation Sources

#### GET /master-data/reservation-sources
Get all reservation sources.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservation sources retrieved successfully",
  "data": [
    {
      "resvSourceId": "RS001",
      "resvSourceName": "Direct",
      "resvSourceDesc": "Direct booking",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "resvSourceId": "RS002",
      "resvSourceName": "Online",
      "resvSourceDesc": "Online booking platform",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/reservation-sources
Create a new reservation source.

**Request:**
\`\`\`json
{
  "resvSourceName": "Travel Agent",
  "resvSourceDesc": "Booking through travel agent",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reservation source created successfully",
  "data": {
    "resvSourceId": "RS003",
    "resvSourceName": "Travel Agent",
    "resvSourceDesc": "Booking through travel agent",
    "isActive": true,
    "createdDate": "2024-01-15T23:30:00"
  }
}
\`\`\`

### Reference Modes

#### GET /master-data/reference-modes
Get all reference modes.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reference modes retrieved successfully",
  "data": [
    {
      "refModeId": "RM001",
      "refModeName": "Friend",
      "refModeDesc": "Referred by friend",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    },
    {
      "refModeId": "RM002",
      "refModeName": "Advertisement",
      "refModeDesc": "Found through advertisement",
      "isActive": true,
      "createdDate": "2024-01-01T00:00:00"
    }
  ]
}
\`\`\`

#### POST /master-data/reference-modes
Create a new reference mode.

**Request:**
\`\`\`json
{
  "refModeName": "Social Media",
  "refModeDesc": "Found through social media",
  "isActive": true
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Reference mode created successfully",
  "data": {
    "refModeId": "RM003",
    "refModeName": "Social Media",
    "refModeDesc": "Found through social media",
    "isActive": true,
    "createdDate": "2024-01-16T00:00:00"
  }
}
\`\`\`

---

## Dashboard Controller

Base path: `/dashboard`

### GET /dashboard/stats
Get dashboard statistics.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Dashboard statistics retrieved",
  "data": {
    "totalRooms": 50,
    "vacantRooms": 25,
    "occupiedRooms": 20,
    "blockedRooms": 5,
    "inHouseGuests": 35,
    "walkInGuests": 8,
    "todayArrivals": 12,
    "todayDepartures": 8,
    "todayCheckOuts": 6,
    "pendingCheckIns": 4
  }
}
\`\`\`

### GET /dashboard/room-status
Get room status summary.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room status summary retrieved",
  "data": {
    "vacant": [
      {
        "roomId": "RM101",
        "roomNo": "101",
        "roomTypeId": "RT001",
        "floor": "1",
        "roomStatus": "VACANT"
      }
    ],
    "occupied": [
      {
        "roomId": "RM201",
        "roomNo": "201",
        "roomTypeId": "RT002",
        "floor": "2",
        "roomStatus": "OCCUPIED"
      }
    ],
    "blocked": [
      {
        "roomId": "RM301",
        "roomNo": "301",
        "roomTypeId": "RT001",
        "floor": "3",
        "roomStatus": "BLOCKED"
      }
    ]
  }
}
\`\`\`

### GET /dashboard/occupancy-rate
Get occupancy rate.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Occupancy rate calculated",
  "data": {
    "totalRooms": 50,
    "occupiedRooms": 20,
    "occupancyRate": 40.0
  }
}
\`\`\`

---

## Advance Controller

Base path: `/advances`

### GET /advances
Get all advances.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advances retrieved successfully",
  "data": [
    {
      "receiptNo": "ADV001",
      "folioNo": "FOL001",
      "reservationNo": "RES001",
      "billNo": null,
      "guestName": "John Doe",
      "advanceAmount": 200.00,
      "advanceDate": "2024-01-20T14:00:00",
      "paymentMode": "CASH",
      "remarks": "Advance payment for reservation",
      "createdDate": "2024-01-20T14:00:00"
    }
  ]
}
\`\`\`

### GET /advances/{receiptNo}
Get advance by receipt number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advance retrieved successfully",
  "data": {
    "receiptNo": "ADV001",
    "folioNo": "FOL001",
    "reservationNo": "RES001",
    "guestName": "John Doe",
    "advanceAmount": 200.00,
    "advanceDate": "2024-01-20T14:00:00",
    "paymentMode": "CASH",
    "remarks": "Advance payment for reservation"
  }
}
\`\`\`

### GET /advances/by-folio/{folioNo}
Get advances by folio number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advances retrieved by folio number",
  "data": [
    {
      "receiptNo": "ADV001",
      "folioNo": "FOL001",
      "advanceAmount": 200.00,
      "advanceDate": "2024-01-20T14:00:00",
      "paymentMode": "CASH"
    }
  ]
}
\`\`\`

### GET /advances/by-reservation/{reservationNo}
Get advances by reservation number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advances retrieved by reservation number",
  "data": [
    {
      "receiptNo": "ADV001",
      "reservationNo": "RES001",
      "advanceAmount": 200.00,
      "advanceDate": "2024-01-20T14:00:00",
      "paymentMode": "CASH"
    }
  ]
}
\`\`\`

### GET /advances/by-bill/{billNo}
Get advances by bill number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advances retrieved by bill number",
  "data": [
    {
      "receiptNo": "ADV002",
      "billNo": "BILL001",
      "advanceAmount": 150.00,
      "advanceDate": "2024-01-25T10:00:00",
      "paymentMode": "CARD"
    }
  ]
}
\`\`\`

### GET /advances/total/by-folio/{folioNo}
Get total advances by folio number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Total advances calculated",
  "data": 350.00
}
\`\`\`

### GET /advances/total/by-reservation/{reservationNo}
Get total advances by reservation number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Total advances calculated",
  "data": 200.00
}
\`\`\`

### GET /advances/total/by-bill/{billNo}
Get total advances by bill number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Total advances calculated",
  "data": 150.00
}
\`\`\`

### POST /advances/reservation
Create advance for reservation.

**Request:**
\`\`\`json
{
  "reservationNo": "RES001",
  "guestName": "John Doe",
  "advanceAmount": 300.00,
  "paymentMode": "CARD",
  "remarks": "Additional advance payment"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advance created for reservation",
  "data": {
    "receiptNo": "ADV003",
    "reservationNo": "RES001",
    "guestName": "John Doe",
    "advanceAmount": 300.00,
    "advanceDate": "2024-01-16T10:00:00",
    "paymentMode": "CARD",
    "remarks": "Additional advance payment",
    "createdDate": "2024-01-16T10:00:00"
  }
}
\`\`\`

### POST /advances/inhouse
Create advance for in-house guest.

**Request:**
\`\`\`json
{
  "folioNo": "FOL001",
  "guestName": "John Doe",
  "advanceAmount": 250.00,
  "paymentMode": "CASH",
  "remarks": "In-house advance payment"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advance created for in-house guest",
  "data": {
    "receiptNo": "ADV004",
    "folioNo": "FOL001",
    "guestName": "John Doe",
    "advanceAmount": 250.00,
    "advanceDate": "2024-01-16T11:00:00",
    "paymentMode": "CASH",
    "remarks": "In-house advance payment",
    "createdDate": "2024-01-16T11:00:00"
  }
}
\`\`\`

### POST /advances/checkout
Create advance for checked-out guest.

**Request:**
\`\`\`json
{
  "billNo": "BILL001",
  "guestName": "John Doe",
  "advanceAmount": 100.00,
  "paymentMode": "CARD",
  "remarks": "Post-checkout advance"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advance created for checked-out guest",
  "data": {
    "receiptNo": "ADV005",
    "billNo": "BILL001",
    "guestName": "John Doe",
    "advanceAmount": 100.00,
    "advanceDate": "2024-01-16T12:00:00",
    "paymentMode": "CARD",
    "remarks": "Post-checkout advance",
    "createdDate": "2024-01-16T12:00:00"
  }
}
\`\`\`

### PUT /advances/{receiptNo}
Update advance.

**Request:**
\`\`\`json
{
  "advanceAmount": 350.00,
  "paymentMode": "CARD",
  "remarks": "Updated advance amount"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advance updated successfully",
  "data": {
    "receiptNo": "ADV003",
    "advanceAmount": 350.00,
    "paymentMode": "CARD",
    "remarks": "Updated advance amount",
    "modifiedDate": "2024-01-16T13:00:00"
  }
}
\`\`\`

### DELETE /advances/{receiptNo}
Delete advance.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Advance deleted successfully"
}
\`\`\`

### GET /advances/total/by-date/{date}
Get total advances for a specific date.

**Path Parameters:**
- `date` (string, required): Date in YYYY-MM-DD format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Total advances for date calculated",
  "data": 1200.00
}
\`\`\`

---

## Post Transaction Controller

Base path: `/transactions`

### GET /transactions
Get all transactions.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Transactions retrieved successfully",
  "data": [
    {
      "transactionId": "TXN001",
      "folioNo": "FOL001",
      "billNo": "BILL001",
      "roomId": "RM101",
      "accountHeadId": "AH001",
      "transactionDate": "2024-01-20T18:00:00",
      "transactionAmount": 150.00,
      "transactionDesc": "Room charge for 20-Jan-2024",
      "auditDate": "2024-01-20",
      "createdDate": "2024-01-20T18:00:00"
    }
  ]
}
\`\`\`

### GET /transactions/{transactionId}
Get transaction by ID.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Transaction retrieved successfully",
  "data": {
    "transactionId": "TXN001",
    "folioNo": "FOL001",
    "billNo": "BILL001",
    "roomId": "RM101",
    "accountHeadId": "AH001",
    "transactionDate": "2024-01-20T18:00:00",
    "transactionAmount": 150.00,
    "transactionDesc": "Room charge for 20-Jan-2024",
    "auditDate": "2024-01-20",
    "createdDate": "2024-01-20T18:00:00"
  }
}
\`\`\`

### GET /transactions/by-folio/{folioNo}
Get transactions by folio number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Transactions retrieved by folio number",
  "data": [
    {
      "transactionId": "TXN001",
      "folioNo": "FOL001",
      "accountHeadId": "AH001",
      "transactionAmount": 150.00,
      "transactionDesc": "Room charge for 20-Jan-2024",
      "transactionDate": "2024-01-20T18:00:00"
    }
  ]
}
\`\`\`

### GET /transactions/by-bill/{billNo}
Get transactions by bill number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Transactions retrieved by bill number",
  "data": [
    {
      "transactionId": "TXN001",
      "billNo": "BILL001",
      "transactionAmount": 150.00,
      "transactionDesc": "Room charge for 20-Jan-2024",
      "transactionDate": "2024-01-20T18:00:00"
    }
  ]
}
\`\`\`

### GET /transactions/by-room/{roomId}
Get transactions by room ID.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Transactions retrieved by room ID",
  "data": [
    {
      "transactionId": "TXN001",
      "roomId": "RM101",
      "transactionAmount": 150.00,
      "transactionDesc": "Room charge for 20-Jan-2024",
      "transactionDate": "2024-01-20T18:00:00"
    }
  ]
}
\`\`\`

### GET /transactions/total/by-folio/{folioNo}
Get total transactions by folio number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Total transactions calculated",
  "data": 750.00
}
\`\`\`

### GET /transactions/total/by-bill/{billNo}
Get total transactions by bill number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Total transactions calculated",
  "data": 750.00
}
\`\`\`

### POST /transactions/inhouse
Create in-house transaction.

**Request:**
\`\`\`json
{
  "folioNo": "FOL001",
  "roomId": "RM101",
  "accountHeadId": "AH002",
  "transactionAmount": 50.00,
  "transactionDesc": "Restaurant charges",
  "auditDate": "2024-01-21"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "In-house transaction created",
  "data": {
    "transactionId": "TXN002",
    "folioNo": "FOL001",
    "roomId": "RM101",
    "accountHeadId": "AH002",
    "transactionAmount": 50.00,
    "transactionDesc": "Restaurant charges",
    "transactionDate": "2024-01-21T14:00:00",
    "auditDate": "2024-01-21",
    "createdDate": "2024-01-21T14:00:00"
  }
}
\`\`\`

### POST /transactions/checkout
Create checkout transaction.

**Request:**
\`\`\`json
{
  "folioNo": "FOL001",
  "billNo": "BILL001",
  "roomId": "RM101",
  "accountHeadId": "AH003",
  "transactionAmount": 25.00,
  "transactionDesc": "Laundry charges",
  "auditDate": "2024-01-25"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Checkout transaction created",
  "data": {
    "transactionId": "TXN003",
    "folioNo": "FOL001",
    "billNo": "BILL001",
    "roomId": "RM101",
    "accountHeadId": "AH003",
    "transactionAmount": 25.00,
    "transactionDesc": "Laundry charges",
    "transactionDate": "2024-01-25T10:00:00",
    "auditDate": "2024-01-25",
    "createdDate": "2024-01-25T10:00:00"
  }
}
\`\`\`

### PUT /transactions/{transactionId}
Update transaction.

**Request:**
\`\`\`json
{
  "transactionAmount": 60.00,
  "transactionDesc": "Updated restaurant charges",
  "auditDate": "2024-01-21"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Transaction updated successfully",
  "data": {
    "transactionId": "TXN002",
    "transactionAmount": 60.00,
    "transactionDesc": "Updated restaurant charges",
    "auditDate": "2024-01-21",
    "modifiedDate": "2024-01-21T15:00:00"
  }
}
\`\`\`

### DELETE /transactions/{transactionId}
Delete transaction.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Transaction deleted successfully"
}
\`\`\`

### GET /transactions/by-audit-date/{auditDate}
Get transactions by audit date.

**Path Parameters:**
- `auditDate` (string, required): Audit date in YYYY-MM-DD format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Transactions retrieved by audit date",
  "data": [
    {
      "transactionId": "TXN001",
      "folioNo": "FOL001",
      "transactionAmount": 150.00,
      "transactionDesc": "Room charge for 20-Jan-2024",
      "auditDate": "2024-01-20"
    }
  ]
}
\`\`\`

### POST /transactions/post-room-charges/{auditDate}
Post room charges for audit date.

**Path Parameters:**
- `auditDate` (string, required): Audit date in YYYY-MM-DD format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Room charges posted for audit date"
}
\`\`\`

---

## Shift Controller

Base path: `/shifts`

### GET /shifts
Get all shifts.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Shifts retrieved successfully",
  "data": [
    {
      "shiftId": "SH001",
      "shiftDate": "2024-01-20",
      "shiftNo": "1",
      "balance": 1500.00,
      "createdDate": "2024-01-20T06:00:00"
    }
  ]
}
\`\`\`

### GET /shifts/{shiftNo}
Get shift by shift number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Shift retrieved successfully",
  "data": {
    "shiftId": "SH001",
    "shiftDate": "2024-01-20",
    "shiftNo": "1",
    "balance": 1500.00,
    "createdDate": "2024-01-20T06:00:00"
  }
}
\`\`\`

### GET /shifts/by-date/{shiftDate}
Get shifts by date.

**Path Parameters:**
- `shiftDate` (string, required): Shift date in YYYY-MM-DD format

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Shifts retrieved by date",
  "data": [
    {
      "shiftId": "SH001",
      "shiftDate": "2024-01-20",
      "shiftNo": "1",
      "balance": 1500.00
    },
    {
      "shiftId": "SH002",
      "shiftDate": "2024-01-20",
      "shiftNo": "2",
      "balance": 2000.00
    }
  ]
}
\`\`\`

### POST /shifts/create-or-update?shiftDate={date}&shiftNo={no}&balance={amount}
Create or update shift.

**Query Parameters:**
- `shiftDate` (string, required): Shift date in YYYY-MM-DD format
- `shiftNo` (string, required): Shift number
- `balance` (number, required): Shift balance

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Shift created/updated successfully",
  "data": {
    "shiftId": "SH003",
    "shiftDate": "2024-01-21",
    "shiftNo": "1",
    "balance": 1800.00,
    "createdDate": "2024-01-21T06:00:00"
  }
}
\`\`\`

### GET /shifts/current-shift-no
Get current shift number.

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Current shift number retrieved",
  "data": "2"
}
\`\`\`

### POST /shifts/process-shift-change?shiftDate={date}&shiftNo={no}&balance={amount}
Process shift change.

**Query Parameters:**
- `shiftDate` (string, required): Shift date in YYYY-MM-DD format
- `shiftNo` (string, required): New shift number
- `balance` (number, required): Shift balance

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Shift change processed successfully"
}
\`\`\`

---

## Password Controller

Base path: `/password`

### POST /password/change
Change user password.

**Headers:**
\`\`\`
Authorization: Bearer <jwt_token>
\`\`\`

**Request:**
\`\`\`json
{
  "currentPassword": "oldpassword123",
  "newPassword": "newpassword456"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Password changed successfully"
}
\`\`\`

### POST /password/reset/{userId}?newPassword={password}
Reset user password (Admin only).

**Path Parameters:**
- `userId` (string, required): User ID

**Query Parameters:**
- `newPassword` (string, required): New password

**Response:**
\`\`\`json
{
  "success": true,
  "message": "Password reset successfully"
}
\`\`\`

---

## Status Codes and Error Examples

### 400 Bad Request
\`\`\`json
{
  "success": false,
  "message": "Validation failed",
  "data": null,
  "error": "Guest name is required"
}
\`\`\`

### 401 Unauthorized
\`\`\`json
{
  "success": false,
  "message": "Authentication required",
  "data": null,
  "error": "JWT token is missing or invalid"
}
\`\`\`

### 403 Forbidden
\`\`\`json
{
  "success": false,
  "message": "Access denied",
  "data": null,
  "error": "Insufficient permissions to access this resource"
}
\`\`\`

### 404 Not Found
\`\`\`json
{
  "success": false,
  "message": "Resource not found",
  "data": null,
  "error": "Reservation with number RES999 not found"
}
\`\`\`

### 500 Internal Server Error
\`\`\`json
{
  "success": false,
  "message": "Internal server error",
  "data": null,
  "error": "An unexpected error occurred while processing the request"
}
\`\`\`

---

## Rate Limiting and Pagination

### Rate Limiting
The API implements rate limiting to prevent abuse:
- **Authentication endpoints**: 5 requests per minute per IP
- **General endpoints**: 100 requests per minute per user
- **Bulk operations**: 10 requests per minute per user

### Pagination
For endpoints returning large datasets, pagination is supported:

**Query Parameters:**
- `page` (integer, default: 0): Page number (0-based)
- `size` (integer, default: 20): Page size (max: 100)
- `sort` (string, optional): Sort field and direction (e.g., "createdDate,desc")

**Paginated Response:**
\`\`\`json
{
  "success": true,
  "message": "Data retrieved successfully",
  "data": {
    "content": [ /* array of items */ ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 20,
      "sort": {
        "sorted": true,
        "unsorted": false
      }
    },
    "totalElements": 150,
    "totalPages": 8,
    "first": true,
    "last": false,
    "numberOfElements": 20
  }
}
\`\`\`

---

## Postman Collection

A comprehensive Postman collection is available with all endpoints, example requests, and environment variables. Import the collection to quickly test all API endpoints.

**Collection Features:**
- Pre-configured authentication
- Environment variables for different deployment stages
- Example requests for all endpoints
- Automated tests for response validation

---

## Support and Contact

For API support, documentation updates, or technical assistance:

- **Email**: support@hotelmanagement.com
- **Documentation**: [API Documentation Portal]
- **Status Page**: [System Status]
- **GitHub Issues**: [Report Issues]

---

**Last Updated**: January 16, 2024  
**API Version**: 1.0.0  
**Documentation Version**: 1.0.0
