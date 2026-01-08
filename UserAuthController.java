package com.TaskMange.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Grouped imports

import com.TaskMange.DTO.AuthResponseDTO;
import com.TaskMange.DTO.LoginRequestDTO;
import com.TaskMange.DTO.RegisterRequestDTO;
import com.TaskMange.DTO.ForgetPasswordDTO;     // Added missing import
import com.TaskMange.DTO.ResetPasswordDTO;      // Added missing import
import com.TaskMange.DTO.LoggedOutRequestDTO;   // Added missing import
import com.TaskMange.Service.UserAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Authentication")
@RequiredArgsConstructor // Automatically injects the 'final' userService
public class UserAuthController {
    
    // Marked as final for @RequiredArgsConstructor to work (best practice)
    private final UserAuthService userService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO register) {
        userService.register(register);
        return ResponseEntity.ok("Register successful");
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO login) {
        return ResponseEntity.ok(userService.login(login));
    }
    
    @PostMapping("/forget_password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordDTO forgetpassword) {
        // Fixed: variable name was 'forgetpassword', not 'forgotpassword'
        // Fixed: field name access (assuming it's getUserOfficialEmail() or public)
        userService.forgetPassword(forgetpassword.getUserOfficialEmail()); 
        return ResponseEntity.ok("Reset password email sent on your Email");
    }
    
    @PostMapping("/reset_password")
    public ResponseEntity<String> resetpassword(@RequestBody ResetPasswordDTO resetpassword) {
        // Fixed: variable case sensitivity (resetpassword vs resetPassword)
        userService.resetPassword(resetpassword.getToken(), resetpassword.getNewpassword());
        return ResponseEntity.ok("Password reset Successful");
    }
    
    @PostMapping("/loggedOut")
    public ResponseEntity<String> loggedOut(
            @RequestBody LoggedOutRequestDTO loggedOut, 
            @RequestHeader("Authorization") String authHeader) {
        
        // Fixed: 'string' to 'String'
        String token = authHeader.substring(7);
        
        // Passing the DTO (and potentially the token) to the service
        userService.loggedout(loggedOut); 
        
        return ResponseEntity.ok("Logged out Successful");
    }
}