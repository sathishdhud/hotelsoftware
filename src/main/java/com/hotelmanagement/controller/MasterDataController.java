package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.*;
import com.hotelmanagement.service.MasterDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master-data")
@CrossOrigin(origins = "*")
public class MasterDataController {

    @Autowired
    private MasterDataService masterDataService;

    // Room Types
    @GetMapping("/room-types")
    public ResponseEntity<ApiResponse<List<RoomType>>> getAllRoomTypes() {
        List<RoomType> roomTypes = masterDataService.getAllRoomTypes();
        return ResponseEntity.ok(ApiResponse.success("Room types retrieved successfully", roomTypes));
    }

    @PostMapping("/room-types")
    public ResponseEntity<ApiResponse<RoomType>> createRoomType(@Valid @RequestBody RoomType roomType) {
        RoomType created = masterDataService.createRoomType(roomType);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Room type created successfully", created));
    }

    @PutMapping("/room-types/{typeId}")
    public ResponseEntity<ApiResponse<RoomType>> updateRoomType(
            @PathVariable String typeId, @Valid @RequestBody RoomType roomType) {
        RoomType updated = masterDataService.updateRoomType(typeId, roomType);
        return ResponseEntity.ok(ApiResponse.success("Room type updated successfully", updated));
    }

    // Companies
    @GetMapping("/companies")
    public ResponseEntity<ApiResponse<List<Company>>> getAllCompanies() {
        List<Company> companies = masterDataService.getAllCompanies();
        return ResponseEntity.ok(ApiResponse.success("Companies retrieved successfully", companies));
    }

    @PostMapping("/companies")
    public ResponseEntity<ApiResponse<Company>> createCompany(@Valid @RequestBody Company company) {
        Company created = masterDataService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Company created successfully", created));
    }

    @PutMapping("/companies/{companyId}")
    public ResponseEntity<ApiResponse<Company>> updateCompany(
            @PathVariable String companyId, @Valid @RequestBody Company company) {
        Company updated = masterDataService.updateCompany(companyId, company);
        return ResponseEntity.ok(ApiResponse.success("Company updated successfully", updated));
    }

    // Plan Types
    @GetMapping("/plan-types")
    public ResponseEntity<ApiResponse<List<PlanType>>> getAllPlanTypes() {
        List<PlanType> planTypes = masterDataService.getAllPlanTypes();
        return ResponseEntity.ok(ApiResponse.success("Plan types retrieved successfully", planTypes));
    }

    @PostMapping("/plan-types")
    public ResponseEntity<ApiResponse<PlanType>> createPlanType(@Valid @RequestBody PlanType planType) {
        PlanType created = masterDataService.createPlanType(planType);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Plan type created successfully", created));
    }

    // Bill Settlement Types
    @GetMapping("/settlement-types")
    public ResponseEntity<ApiResponse<List<BillSettlementType>>> getAllBillSettlementTypes() {
        List<BillSettlementType> types = masterDataService.getAllBillSettlementTypes();
        return ResponseEntity.ok(ApiResponse.success("Settlement types retrieved successfully", types));
    }

    @PostMapping("/settlement-types")
    public ResponseEntity<ApiResponse<BillSettlementType>> createBillSettlementType(@Valid @RequestBody BillSettlementType type) {
        BillSettlementType created = masterDataService.createBillSettlementType(type);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Settlement type created successfully", created));
    }

    // Account Heads
    @GetMapping("/account-heads")
    public ResponseEntity<ApiResponse<List<HotelAccountHead>>> getAllAccountHeads() {
        List<HotelAccountHead> accountHeads = masterDataService.getAllAccountHeads();
        return ResponseEntity.ok(ApiResponse.success("Account heads retrieved successfully", accountHeads));
    }

    @PostMapping("/account-heads")
    public ResponseEntity<ApiResponse<HotelAccountHead>> createAccountHead(@Valid @RequestBody HotelAccountHead accountHead) {
        HotelAccountHead created = masterDataService.createAccountHead(accountHead);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account head created successfully", created));
    }

    // Taxation
    @GetMapping("/taxes")
    public ResponseEntity<ApiResponse<List<Taxation>>> getAllTaxes() {
        List<Taxation> taxes = masterDataService.getAllTaxes();
        return ResponseEntity.ok(ApiResponse.success("Taxes retrieved successfully", taxes));
    }

    @PostMapping("/taxes")
    public ResponseEntity<ApiResponse<Taxation>> createTaxation(@Valid @RequestBody Taxation taxation) {
        Taxation created = masterDataService.createTaxation(taxation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tax created successfully", created));
    }

    // User Types
    @GetMapping("/user-types")
    public ResponseEntity<ApiResponse<List<UserType>>> getAllUserTypes() {
        List<UserType> userTypes = masterDataService.getAllUserTypes();
        return ResponseEntity.ok(ApiResponse.success("User types retrieved successfully", userTypes));
    }

    @PostMapping("/user-types")
    public ResponseEntity<ApiResponse<UserType>> createUserType(@Valid @RequestBody UserType userType) {
        UserType created = masterDataService.createUserType(userType);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User type created successfully", created));
    }

    // Nationalities
    @GetMapping("/nationalities")
    public ResponseEntity<ApiResponse<List<Nationality>>> getAllNationalities() {
        List<Nationality> nationalities = masterDataService.getAllNationalities();
        return ResponseEntity.ok(ApiResponse.success("Nationalities retrieved successfully", nationalities));
    }

    @PostMapping("/nationalities")
    public ResponseEntity<ApiResponse<Nationality>> createNationality(@Valid @RequestBody Nationality nationality) {
        Nationality created = masterDataService.createNationality(nationality);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Nationality created successfully", created));
    }

    // Arrival Modes
    @GetMapping("/arrival-modes")
    public ResponseEntity<ApiResponse<List<ArrivalMode>>> getAllArrivalModes() {
        List<ArrivalMode> arrivalModes = masterDataService.getAllArrivalModes();
        return ResponseEntity.ok(ApiResponse.success("Arrival modes retrieved successfully", arrivalModes));
    }

    @PostMapping("/arrival-modes")
    public ResponseEntity<ApiResponse<ArrivalMode>> createArrivalMode(@Valid @RequestBody ArrivalMode arrivalMode) {
        ArrivalMode created = masterDataService.createArrivalMode(arrivalMode);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Arrival mode created successfully", created));
    }

    // Reservation Sources
    @GetMapping("/reservation-sources")
    public ResponseEntity<ApiResponse<List<ResvSource>>> getAllReservationSources() {
        List<ResvSource> sources = masterDataService.getAllReservationSources();
        return ResponseEntity.ok(ApiResponse.success("Reservation sources retrieved successfully", sources));
    }

    @PostMapping("/reservation-sources")
    public ResponseEntity<ApiResponse<ResvSource>> createReservationSource(@Valid @RequestBody ResvSource resvSource) {
        ResvSource created = masterDataService.createReservationSource(resvSource);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Reservation source created successfully", created));
    }

    // Reference Modes
    @GetMapping("/reference-modes")
    public ResponseEntity<ApiResponse<List<RefMode>>> getAllReferenceModes() {
        List<RefMode> refModes = masterDataService.getAllReferenceModes();
        return ResponseEntity.ok(ApiResponse.success("Reference modes retrieved successfully", refModes));
    }

    @PostMapping("/reference-modes")
    public ResponseEntity<ApiResponse<RefMode>> createReferenceMode(@Valid @RequestBody RefMode refMode) {
        RefMode created = masterDataService.createReferenceMode(refMode);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Reference mode created successfully", created));
    }
}
