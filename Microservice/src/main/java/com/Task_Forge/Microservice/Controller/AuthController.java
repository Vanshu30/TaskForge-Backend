package com.Task_Forge.Microservice.Controller;

import com.Task_Forge.Microservice.DTO.LoginRequest;
import com.Task_Forge.Microservice.DTO.SignupRequest;
import com.Task_Forge.Microservice.ENUM.RoleType;
import com.Task_Forge.Microservice.Entity.Role;
import com.Task_Forge.Microservice.Entity.User;
import com.Task_Forge.Microservice.Repository.UserRepository;
import com.Task_Forge.Microservice.Security.JwtTokenProvider;
import com.Task_Forge.Microservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        try {
            if (signupRequest.getName() == null || signupRequest.getEmail() == null || signupRequest.getPassword() == null || signupRequest.getUsername() == null) {
                return ResponseEntity.badRequest().body("All fields are required");
            }

            User user = new User();
            user.setName(signupRequest.getName());
            user.setEmail(signupRequest.getEmail());
            user.setUsername(signupRequest.getUsername());

            // 🔐 Hash the password before saving
            String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
            user.setPassword(hashedPassword);

            // ✅ Correctly set enum value from request
            RoleType role = signupRequest.getRole() != null ? signupRequest.getRole() : RoleType.USER;
            user.setRole(role);


            userRepository.save(user);
            return ResponseEntity.ok("registration success");
        } catch (Exception e) {
            e.printStackTrace(); // For development; replace with proper logger in prod
            return ResponseEntity.status(500).body("User registration failed: " + e.getMessage());
        }

    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and Password are required");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
    }

}
