package com.hotelmanagement.service;

import com.hotelmanagement.entity.HotelSoftUser;
import com.hotelmanagement.entity.UserRightsData;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.HotelSoftUserRepository;
import com.hotelmanagement.repository.UserRightsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private HotelSoftUserRepository userRepository;

    @Autowired
    private UserRightsDataRepository userRightsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<HotelSoftUser> getAllUsers() {
        return userRepository.findAll();
    }

    public HotelSoftUser getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    public HotelSoftUser getUserByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    public List<HotelSoftUser> getUsersByHotel(String hotelId) {
        return userRepository.findByHotelId(hotelId);
    }

    public List<HotelSoftUser> getUsersByUserType(String userTypeId) {
        return userRepository.findByUserTypeId(userTypeId);
    }

    public HotelSoftUser createUser(HotelSoftUser user) {
        validateUser(user);
        
        // Check if username already exists
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new BadRequestException("Username already exists: " + user.getUserName());
        }
        
        // Generate user ID and encode password
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
    }

    public HotelSoftUser updateUser(String userId, HotelSoftUser updatedUser) {
        HotelSoftUser existingUser = getUserById(userId);
        
        validateUser(updatedUser);
        
        // Check if username is being changed and if it already exists
        if (!existingUser.getUserName().equals(updatedUser.getUserName())) {
            if (userRepository.existsByUserName(updatedUser.getUserName())) {
                throw new BadRequestException("Username already exists: " + updatedUser.getUserName());
            }
        }
        
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setUserTypeId(updatedUser.getUserTypeId());
        existingUser.setHotelId(updatedUser.getHotelId());
        
        // Only update password if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        
        return userRepository.save(existingUser);
    }

    public void deleteUser(String userId) {
        HotelSoftUser user = getUserById(userId);
        
        // Delete user rights first
        List<UserRightsData> userRights = userRightsRepository.findByUserId(userId);
        userRightsRepository.deleteAll(userRights);
        
        userRepository.delete(user);
    }

    public List<UserRightsData> getUserRights(String userId) {
        return userRightsRepository.findByUserId(userId);
    }

    public List<String> getUserAccessibleModules(String userId) {
        return userRightsRepository.findAccessibleModulesByUserId(userId);
    }

    public UserRightsData setUserRight(String userId, String moduleName, String permissionType) {
        validatePermissionType(permissionType);
        
        // Check if user exists
        getUserById(userId);
        
        // Check if right already exists
        var existingRight = userRightsRepository.findByUserIdAndModuleName(userId, moduleName);
        
        if (existingRight.isPresent()) {
            // Update existing right
            UserRightsData right = existingRight.get();
            right.setPermissionType(permissionType);
            return userRightsRepository.save(right);
        } else {
            // Create new right
            UserRightsData newRight = new UserRightsData();
            newRight.setId(UUID.randomUUID().toString());
            newRight.setUserId(userId);
            newRight.setModuleName(moduleName);
            newRight.setPermissionType(permissionType);
            return userRightsRepository.save(newRight);
        }
    }

    public boolean hasPermission(String userId, String moduleName, String permissionType) {
        var userRight = userRightsRepository.findByUserIdAndModuleNameAndPermissionType(userId, moduleName, permissionType);
        return userRight.isPresent();
    }

    public boolean canRead(String userId, String moduleName) {
        return hasPermission(userId, moduleName, "read") || hasPermission(userId, moduleName, "write");
    }

    public boolean canWrite(String userId, String moduleName) {
        return hasPermission(userId, moduleName, "write");
    }

    private void validateUser(HotelSoftUser user) {
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw new BadRequestException("Username is required");
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password is required");
        }
        
        if (user.getUserTypeId() == null || user.getUserTypeId().trim().isEmpty()) {
            throw new BadRequestException("User type is required");
        }
    }

    private void validatePermissionType(String permissionType) {
        if (!List.of("read", "write", "none").contains(permissionType)) {
            throw new BadRequestException("Invalid permission type. Must be one of: read, write, none");
        }
    }
}
