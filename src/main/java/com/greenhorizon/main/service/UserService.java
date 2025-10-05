package com.greenhorizon.main.service;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenhorizon.main.entity.User;
import com.greenhorizon.main.repository.UserRepository;

import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;
//  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_CUSTOMER");
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

	
    
    
    
    
    
}
