package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.dto.ChangePasswordRequest;
import com.hotelmanagement.security.UserPrincipal;
import com.hotelmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@CrossOrigin(origins = "*")
public class PasswordController {

    @Autowired
    private AuthService authService;

    @PostMapping("/change")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        authService.changePassword(userPrincipal.getUserId(), request.getCurrentPassword(), request.getNewPassword());
        
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully"));
    }

    @PostMapping("/reset/{userId}")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @PathVariable String userId, @RequestParam String newPassword) {
        authService.resetPassword(userId, newPassword);
        
        return ResponseEntity.ok(ApiResponse.success("Password reset successfully"));
    }
}
