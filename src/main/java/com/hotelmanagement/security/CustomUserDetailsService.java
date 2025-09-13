package com.hotelmanagement.security;

import com.hotelmanagement.entity.HotelSoftUser;
import com.hotelmanagement.repository.HotelSoftUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private HotelSoftUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HotelSoftUser user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(String userId) {
        HotelSoftUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        return UserPrincipal.create(user);
    }
}
