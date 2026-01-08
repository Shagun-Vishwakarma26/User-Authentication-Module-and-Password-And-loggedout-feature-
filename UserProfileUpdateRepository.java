package com.TaskMange.Repository;

import java.util.Optional; // Fix 1: Added missing import

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Fix 2: Added missing semicolon

import com.TaskMange.Entity.UserProfileUpdate;

@Repository
public interface UserProfileUpdateRepository extends JpaRepository<UserProfileUpdate, Long> {
    
    // Fix 3: Method name must match the field 'userOfficialEmail' in your entity
    // Fix 4: Added missing semicolon at the end of the line
    Optional<UserProfileUpdate> findByUserOfficialEmail(String userOfficialEmail);
}
