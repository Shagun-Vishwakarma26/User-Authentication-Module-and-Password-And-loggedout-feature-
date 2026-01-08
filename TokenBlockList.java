package com.TaskMange.Entity;

import java.util.Date;

// Fix 1: Removed all 'javax.persistence' imports
// Fix 2: Consolidated imports to 'jakarta.persistence'
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // Optional but recommended

@Entity
@Table(name = "token_block_list") // Best practice to name your table
public class TokenBlockList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Fix 3: Optional but good practice for clarity
    private String token;
    
    // Fix 4: Ensure the date is handled correctly in the DB
    private Date expiry;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }
}