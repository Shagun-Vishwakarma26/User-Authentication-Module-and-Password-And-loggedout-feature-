package com.TaskMange.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Fix: Import PutMapping and RequestBody
import com.TaskMange.Entity.UserProfileUpdate; // Assuming this is your Entity/DTO
import com.TaskMange.Service.UserProfileUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile_update")
@RequiredArgsConstructor
public class UserProfileUpdateController {
    
    // Fix: Using 'final' with @RequiredArgsConstructor is best practice
    private final UserProfileUpdateService userProfileService;
    
    @PutMapping("/updateProfile/{email}") // Added leading slash
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileUpdate profile) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(profile));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<UserProfileUpdate>> getAllUserProfile() {
        // Fix: Changed 'UserProfileService' (Class) to 'userProfileService' (Instance)
        return ResponseEntity.ok(userProfileService.getAllProfile());
    }
    
    @GetMapping("/{email}")
    // Fix: PathVariable name must match the {email} placeholder
    // Fix: Return type 'userProfileUpdate' capitalized to 'UserProfileUpdate'
    public ResponseEntity<UserProfileUpdate> getProfileByEmail(@PathVariable("email") String userOfficialEmail) {
        return ResponseEntity.ok(userProfileService.getProfileByEmail(userOfficialEmail));
    }
}