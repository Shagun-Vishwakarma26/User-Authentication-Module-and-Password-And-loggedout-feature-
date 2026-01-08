package com.TaskMange.Repository;

import java.util.Optional; // Fix 1: Added missing import

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Fix 2: Added missing semicolon

import com.TaskMange.Entity.UserAuth;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    
    // This now matches the field 'userOfficialEmail' in your Entity
    Optional<UserAuth> findByUserOfficialEmail(String userOfficialEmail);
    
    // Fix 3: Changed 'findByRestToken' to 'findByResetToken' 
    // (Matches the field name 'resetToken' in your UserAuth entity)
    Optional<UserAuth> findByResetToken(String resetToken);
}