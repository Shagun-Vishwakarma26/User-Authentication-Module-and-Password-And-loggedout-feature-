package com.TaskMange.Service;

import java.util.List;
import java.util.stream.Collectors; // Fix 1: Added missing import

import org.springframework.stereotype.Service;
import com.TaskMange.Entity.UserProfileUpdate;
import com.TaskMange.Repository.UserProfileUpdateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Automatically injects 'final' fields
public class UserProfileUpdateService {
    
    // Fix 2: Changed to 'final' to work with @RequiredArgsConstructor 
    // and removed @Autowired (best practice).
    private final UserProfileUpdateRepository userProfileRepo;
    
    public String updateUserProfile(UserProfileUpdate profile) {
        // Logic fix: Try to find existing user to update, or create new if not found
        UserProfileUpdate user = userProfileRepo.findByUserOfficialEmail(profile.getUserOfficialEmail())
                .orElse(new UserProfileUpdate());
        
        user.setUserName(profile.getUserName());
        user.setUserOfficialEmail(profile.getUserOfficialEmail());
        user.setOrganizationName(profile.getOrganizationName());
        user.setDepartment(profile.getDepartment());
        user.setDesignation(profile.getDesignation());
        user.setActive(true);
        
        userProfileRepo.save(user);
        return "Update User Profile Successful";
    }
    
    public List<UserProfileUpdate> getAllProfile() {
        // Fix 3: Changed 'UserProfileRepo' (Class) to 'userProfileRepo' (Variable)
        return userProfileRepo.findAll(); 
        // Note: .stream().collect(Collectors.toList()) is redundant here as findAll() returns a List
    }
    
    public UserProfileUpdate getProfileByEmail(String userOfficialEmail) {
        return userProfileRepo.findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userOfficialEmail));
    }
}