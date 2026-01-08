package com.TaskMange.Entity;

import java.time.LocalDateTime;

// Fix 1: Removed javax.persistence and standardized on jakarta
import jakarta.persistence.*;
import lombok.*; // Fix 2: Added missing lombok import

@Entity
@Table(name = "user_profiles")
@Data // Automatically generates getters, setters, toString, equals, and hashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    private String userName;

    // Fix 3: Standardized camelCase (changed 'UserOfficialEmail' to 'userOfficialEmail')
    @Column(unique = true)
    private String userOfficialEmail;
    
    private String department;
    private String designation;
    private String organizationName;
    
    // Fix 4: Standard default value initialization
    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /* Note: All manual Getters and Setters have been removed. 
       Lombok's @Data handles this. This prevents errors where 
       method names don't match variable names.
    */
}