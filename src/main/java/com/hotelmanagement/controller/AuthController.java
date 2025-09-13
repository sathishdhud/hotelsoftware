package com.hotelmanagement.controller;

import com.hotelmanagement.common.ApiResponse;
import com.hotelmanagement.dto.JwtAuthenticationResponse;
import com.hotelmanagement.dto.LoginRequest;
import com.hotelmanagement.dto.UserRegistrationRequest;
import com.hotelmanagement.entity.HotelSoftUser;
import com.hotelmanagement.security.JwtTokenProvider;
import com.hotelmanagement.security.UserPrincipal;
import com.hotelmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        JwtAuthenticationResponse response = new JwtAuthenticationResponse(
            jwt,
            userPrincipal.getUserId(),
            userPrincipal.getUsername(),
            userPrincipal.getUserTypeId(),
            userPrincipal.getHotelId()
        );

        return ResponseEntity.ok(ApiResponse.success("User authenticated successfully", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<HotelSoftUser>> registerUser(@Valid @RequestBody UserRegistrationRequest registrationRequest) {
        HotelSoftUser user = new HotelSoftUser();
        user.setUserName(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        user.setUserTypeId(registrationRequest.getUserTypeId());
        user.setHotelId(registrationRequest.getHotelId());

        HotelSoftUser createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", createdUser));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserPrincipal>> getCurrentUser(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(ApiResponse.success("Current user retrieved", userPrincipal));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> refreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = tokenProvider.generateTokenFromUserId(userPrincipal.getUserId());

        JwtAuthenticationResponse response = new JwtAuthenticationResponse(
            jwt,
            userPrincipal.getUserId(),
            userPrincipal.getUsername(),
            userPrincipal.getUserTypeId(),
            userPrincipal.getHotelId()
        );

        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", response));
    }
}
