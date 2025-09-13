package com.hotelmanagement.service;

import com.hotelmanagement.entity.HotelSoftUser;
import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.repository.HotelSoftUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private HotelSoftUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateUserCredentials(String username, String password) {
        Optional<HotelSoftUser> userOptional = userRepository.findByUserName(username);
        
        if (userOptional.isPresent()) {
            HotelSoftUser user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        
        return false;
    }

    public HotelSoftUser authenticateUser(String username, String password) {
        Optional<HotelSoftUser> userOptional = userRepository.findByUserName(username);
        
        if (userOptional.isPresent()) {
            HotelSoftUser user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        
        throw new BadRequestException("Invalid username or password");
    }

    public void changePassword(String userId, String oldPassword, String newPassword) {
        HotelSoftUser user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void resetPassword(String userId, String newPassword) {
        HotelSoftUser user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
