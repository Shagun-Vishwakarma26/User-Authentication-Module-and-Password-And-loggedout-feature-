package com.TaskMange.Service;

import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.TaskMange.Entity.UserAuth;
import com.TaskMange.Entity.TokenBlockList;
import com.TaskMange.DTO.*; // Assuming your DTOs are here
import com.TaskMange.Enum.Role;
import com.TaskMange.Repository.TokenBlockListRepository;
import com.TaskMange.Repository.UserAuthRepository;
import com.TaskMange.Security.JWTUtil;
import io.jsonwebtoken.Claims;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    
    // Use 'final' with @RequiredArgsConstructor for cleaner injection
    private final UserAuthRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final TokenBlockListRepository tokenBlockRepo;
    private final EmailService emailService;

    public void register(RegisterRequestDTO register) {
        // Correct check: Do this BEFORE saving
        if(userRepo.findByUserOfficialEmail(register.getUserOfficialEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserAuth user = UserAuth.builder()
            .userName(register.getUserName())
            .userOfficialEmail(register.getUserOfficialEmail())
            .password(passwordEncoder.encode(register.getPassword()))
            .role(register.getRole()) // Assumes DTO already has Role enum
            .build();
        
        userRepo.save(user);
    }
    
    public AuthResponseDTO login(LoginRequestDTO login) {
        UserAuth user = userRepo.findByUserOfficialEmail(login.getUserOfficialEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(user);
        return new AuthResponseDTO(token, "Login successful");
    }
    
    public void forgetPassword(String userOfficialEmail) {
        UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = UUID.randomUUID().toString();
        
        user.setResetToken(token);
        // Convert long to Date for resetTokenExpiry
        user.setResetTokenExpiry(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
        userRepo.save(user);
        
        String resetLink = "http://localhost:5050/api/Authentication/reset_password?token=" + token;
        emailService.sentResetEMail(user.getUserOfficialEmail(), resetLink);
    }

    public void resetPassword(String token, String newPassword) {
        UserAuth user = userRepo.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        
        // Logic: If current date is BEFORE expiry, it is still valid
        if(user.getResetTokenExpiry().after(new Date())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            user.setResetTokenExpiry(null);
            userRepo.save(user);
        } else {
            throw new RuntimeException("Token expired");
        }
    }
    
    public void loggedout(LoggedOutRequestDTO loggedOut) {
        Claims claims = jwtUtil.getClaims(loggedOut.getToken());
        
        TokenBlockList killedToken = new TokenBlockList();
        killedToken.setToken(loggedOut.getToken());
        killedToken.setExpiry(claims.getExpiration());
        
        tokenBlockRepo.save(killedToken);
    }
}
