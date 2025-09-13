package com.hotelmanagement.service;

import com.hotelmanagement.entity.*;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MasterDataService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PlanTypeRepository planTypeRepository;

    @Autowired
    private BillSettlementTypeRepository billSettlementTypeRepository;

    @Autowired
    private HotelAccountHeadRepository hotelAccountHeadRepository;

    @Autowired
    private TaxationRepository taxationRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private NationalityRepository nationalityRepository;

    @Autowired
    private ArrivalModeRepository arrivalModeRepository;

    @Autowired
    private ResvSourceRepository resvSourceRepository;

    @Autowired
    private RefModeRepository refModeRepository;

    // Room Type Management
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAllOrderByTypeName();
    }

    public RoomType createRoomType(RoomType roomType) {
        validateRoomType(roomType);
        roomType.setTypeId(UUID.randomUUID().toString());
        return roomTypeRepository.save(roomType);
    }

    public RoomType updateRoomType(String typeId, RoomType updatedRoomType) {
        RoomType existing = roomTypeRepository.findById(typeId)
                .orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        
        validateRoomType(updatedRoomType);
        existing.setTypeName(updatedRoomType.getTypeName());
        existing.setNoOfRooms(updatedRoomType.getNoOfRooms());
        
        return roomTypeRepository.save(existing);
    }

    // Company Management
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company createCompany(Company company) {
        validateCompany(company);
        company.setCompanyId(UUID.randomUUID().toString());
        return companyRepository.save(company);
    }

    public Company updateCompany(String companyId, Company updatedCompany) {
        Company existing = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        
        validateCompany(updatedCompany);
        existing.setCompanyName(updatedCompany.getCompanyName());
        existing.setAddress1(updatedCompany.getAddress1());
        existing.setAddress2(updatedCompany.getAddress2());
        existing.setAddress3(updatedCompany.getAddress3());
        existing.setGstNumber(updatedCompany.getGstNumber());
        
        return companyRepository.save(existing);
    }

    // Plan Type Management
    public List<PlanType> getAllPlanTypes() {
        return planTypeRepository.findAllOrderByPlanName();
    }

    public PlanType createPlanType(PlanType planType) {
        validatePlanType(planType);
        planType.setPlanId(UUID.randomUUID().toString());
        return planTypeRepository.save(planType);
    }

    // Bill Settlement Type Management
    public List<BillSettlementType> getAllBillSettlementTypes() {
        return billSettlementTypeRepository.findAllOrderByName();
    }

    public BillSettlementType createBillSettlementType(BillSettlementType type) {
        validateBillSettlementType(type);
        type.setId(UUID.randomUUID().toString());
        return billSettlementTypeRepository.save(type);
    }

    // Hotel Account Head Management
    public List<HotelAccountHead> getAllAccountHeads() {
        return hotelAccountHeadRepository.findAllOrderByName();
    }

    public HotelAccountHead createAccountHead(HotelAccountHead accountHead) {
        validateAccountHead(accountHead);
        accountHead.setAccHeadId(UUID.randomUUID().toString());
        return hotelAccountHeadRepository.save(accountHead);
    }

    // Taxation Management
    public List<Taxation> getAllTaxes() {
        return taxationRepository.findAllOrderByPercentage();
    }

    public Taxation createTaxation(Taxation taxation) {
        taxation.setTaxId(UUID.randomUUID().toString());
        return taxationRepository.save(taxation);
    }

    // User Type Management
    public List<UserType> getAllUserTypes() {
        return userTypeRepository.findAllOrderByTypeName();
    }

    public UserType createUserType(UserType userType) {
        validateUserType(userType);
        userType.setUserTypeId(UUID.randomUUID().toString());
        return userTypeRepository.save(userType);
    }

    // Nationality Management
    public List<Nationality> getAllNationalities() {
        return nationalityRepository.findAllOrderByNationality();
    }

    public Nationality createNationality(Nationality nationality) {
        validateNationality(nationality);
        nationality.setId(UUID.randomUUID().toString());
        return nationalityRepository.save(nationality);
    }

    // Arrival Mode Management
    public List<ArrivalMode> getAllArrivalModes() {
        return arrivalModeRepository.findAllOrderByArrivalMode();
    }

    public ArrivalMode createArrivalMode(ArrivalMode arrivalMode) {
        validateArrivalMode(arrivalMode);
        arrivalMode.setId(UUID.randomUUID().toString());
        return arrivalModeRepository.save(arrivalMode);
    }

    // Reservation Source Management
    public List<ResvSource> getAllReservationSources() {
        return resvSourceRepository.findAllOrderByResvSource();
    }

    public ResvSource createReservationSource(ResvSource resvSource) {
        validateResvSource(resvSource);
        resvSource.setId(UUID.randomUUID().toString());
        return resvSourceRepository.save(resvSource);
    }

    // Reference Mode Management
    public List<RefMode> getAllReferenceModes() {
        return refModeRepository.findAllOrderByRefMode();
    }

    public RefMode createReferenceMode(RefMode refMode) {
        validateRefMode(refMode);
        refMode.setId(UUID.randomUUID().toString());
        return refModeRepository.save(refMode);
    }

    // Validation methods
    private void validateRoomType(RoomType roomType) {
        if (roomType.getTypeName() == null || roomType.getTypeName().trim().isEmpty()) {
            throw new BadRequestException("Room type name is required");
        }
        if (roomType.getNoOfRooms() == null || roomType.getNoOfRooms() <= 0) {
            throw new BadRequestException("Number of rooms must be greater than 0");
        }
    }

    private void validateCompany(Company company) {
        if (company.getCompanyName() == null || company.getCompanyName().trim().isEmpty()) {
            throw new BadRequestException("Company name is required");
        }
    }

    private void validatePlanType(PlanType planType) {
        if (planType.getPlanName() == null || planType.getPlanName().trim().isEmpty()) {
            throw new BadRequestException("Plan name is required");
        }
    }

    private void validateBillSettlementType(BillSettlementType type) {
        if (type.getName() == null || type.getName().trim().isEmpty()) {
            throw new BadRequestException("Settlement type name is required");
        }
    }

    private void validateAccountHead(HotelAccountHead accountHead) {
        if (accountHead.getName() == null || accountHead.getName().trim().isEmpty()) {
            throw new BadRequestException("Account head name is required");
        }
    }

    private void validateUserType(UserType userType) {
        if (userType.getTypeName() == null || userType.getTypeName().trim().isEmpty()) {
            throw new BadRequestException("User type name is required");
        }
    }

    private void validateNationality(Nationality nationality) {
        if (nationality.getNationality() == null || nationality.getNationality().trim().isEmpty()) {
            throw new BadRequestException("Nationality is required");
        }
    }

    private void validateArrivalMode(ArrivalMode arrivalMode) {
        if (arrivalMode.getArrivalMode() == null || arrivalMode.getArrivalMode().trim().isEmpty()) {
            throw new BadRequestException("Arrival mode is required");
        }
    }

    private void validateResvSource(ResvSource resvSource) {
        if (resvSource.getResvSource() == null || resvSource.getResvSource().trim().isEmpty()) {
            throw new BadRequestException("Reservation source is required");
        }
    }

    private void validateRefMode(RefMode refMode) {
        if (refMode.getRefMode() == null || refMode.getRefMode().trim().isEmpty()) {
            throw new BadRequestException("Reference mode is required");
        }
    }
}
