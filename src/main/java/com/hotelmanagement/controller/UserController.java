package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.entity.HotelSoftUser;
import com.hotelmanagement.entity.UserRightsData;
import com.hotelmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HotelSoftUser>>> getAllUsers() {
        List<HotelSoftUser> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<HotelSoftUser>> getUserById(@PathVariable String userId) {
        HotelSoftUser user = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<ApiResponse<HotelSoftUser>> getUserByUsername(@PathVariable String username) {
        HotelSoftUser user = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
    }

    @GetMapping("/by-hotel/{hotelId}")
    public ResponseEntity<ApiResponse<List<HotelSoftUser>>> getUsersByHotel(@PathVariable String hotelId) {
        List<HotelSoftUser> users = userService.getUsersByHotel(hotelId);
        return ResponseEntity.ok(ApiResponse.success("Users retrieved by hotel", users));
    }

    @GetMapping("/by-type/{userTypeId}")
    public ResponseEntity<ApiResponse<List<HotelSoftUser>>> getUsersByUserType(@PathVariable String userTypeId) {
        List<HotelSoftUser> users = userService.getUsersByUserType(userTypeId);
        return ResponseEntity.ok(ApiResponse.success("Users retrieved by type", users));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HotelSoftUser>> createUser(@Valid @RequestBody HotelSoftUser user) {
        HotelSoftUser createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", createdUser));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<HotelSoftUser>> updateUser(
            @PathVariable String userId, @Valid @RequestBody HotelSoftUser user) {
        HotelSoftUser updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }

    @GetMapping("/{userId}/rights")
    public ResponseEntity<ApiResponse<List<UserRightsData>>> getUserRights(@PathVariable String userId) {
        List<UserRightsData> rights = userService.getUserRights(userId);
        return ResponseEntity.ok(ApiResponse.success("User rights retrieved", rights));
    }

    @GetMapping("/{userId}/modules")
    public ResponseEntity<ApiResponse<List<String>>> getUserAccessibleModules(@PathVariable String userId) {
        List<String> modules = userService.getUserAccessibleModules(userId);
        return ResponseEntity.ok(ApiResponse.success("Accessible modules retrieved", modules));
    }

    @PostMapping("/{userId}/rights")
    public ResponseEntity<ApiResponse<UserRightsData>> setUserRight(
            @PathVariable String userId, @RequestParam String moduleName, @RequestParam String permissionType) {
        UserRightsData right = userService.setUserRight(userId, moduleName, permissionType);
        return ResponseEntity.ok(ApiResponse.success("User right set successfully", right));
    }

    @GetMapping("/{userId}/permissions/{moduleName}")
    public ResponseEntity<ApiResponse<Boolean>> checkPermission(
            @PathVariable String userId, @PathVariable String moduleName, @RequestParam String permissionType) {
        boolean hasPermission = userService.hasPermission(userId, moduleName, permissionType);
        return ResponseEntity.ok(ApiResponse.success("Permission checked", hasPermission));
    }

    @GetMapping("/{userId}/can-read/{moduleName}")
    public ResponseEntity<ApiResponse<Boolean>> canRead(@PathVariable String userId, @PathVariable String moduleName) {
        boolean canRead = userService.canRead(userId, moduleName);
        return ResponseEntity.ok(ApiResponse.success("Read permission checked", canRead));
    }

    @GetMapping("/{userId}/can-write/{moduleName}")
    public ResponseEntity<ApiResponse<Boolean>> canWrite(@PathVariable String userId, @PathVariable String moduleName) {
        boolean canWrite = userService.canWrite(userId, moduleName);
        return ResponseEntity.ok(ApiResponse.success("Write permission checked", canWrite));
    }
}
