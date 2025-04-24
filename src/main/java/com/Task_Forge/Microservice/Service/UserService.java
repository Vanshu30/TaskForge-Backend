package com.Task_Forge.Microservice.Service;

import com.Task_Forge.Microservice.DTO.LoginRequest;
import com.Task_Forge.Microservice.DTO.SignupRequest;
import com.Task_Forge.Microservice.ENUM.RoleType;
import com.Task_Forge.Microservice.Entity.User;
import com.Task_Forge.Microservice.Entity.Role;
import com.Task_Forge.Microservice.Exception.ResourceNotFoundException;
import com.Task_Forge.Microservice.Repository.UserRepository;
import com.Task_Forge.Microservice.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String registerUser(SignupRequest signupRequest) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());

        // 🔐 Hash the password before saving
        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(hashedPassword);

        // Optionally set role or other details
        Role role = new Role();
        role.setName(RoleType.USER.name());


        userRepository.save(user);

        // Return a JWT token after registration
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                signupRequest.getEmail(), signupRequest.getPassword()
        );

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return token;


    }


    @Transactional
    public String authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return jwtTokenProvider.generateToken(authentication);
    }

    public User getUserById(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }
}