package com.TaskMange.Entity;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Assuming the package for Role is correct; ensure the folder is 'Enum' not 'enums'
import com.TaskMange.Enum.Role; 

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_auth") 
@Data
@Builder
@NoArgsConstructor  // Required by JPA
@AllArgsConstructor // Required by @Builder
public class UserAuth {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;
    
    @Column(unique = true, nullable = false)
    private String userOfficialEmail;
    
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    private String resetToken;

    private Date resetTokenExpiry;
}