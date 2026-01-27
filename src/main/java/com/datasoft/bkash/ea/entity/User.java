// User.java (Entity)
package com.datasoft.bkash.ea.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")  // Change to your actual table name
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "email_normalized", nullable = false, unique = true, length = 255)
    private String emailNormalized;

    @Column(name = "email_2fa_verified", nullable = false)
    private Boolean emailVerified = false;

    @Column(name = "email_2fa", nullable = true)
    private String email_2fa;

    @Column(name = "email_2fa_normalized", nullable = true, unique = true, length = 255)
    private String email2faNormalized;

    @Column(name = "phone", length = 20, unique = true)
    private String phone;

    @Column(name = "phone_verified", nullable = false)
    private Boolean phoneVerified = false;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";

    @Column(name = "userType")
    private Integer userType;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt;

    @Column(name = "imageId")
    private Integer imageId;

    @Column(name = "failed_login_count", nullable = false)
    private Integer failedLoginCount = 0;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Column(name = "otp", length = 10)
    private String otp;

    @Column(name = "otp_expires_at")
    private LocalDateTime otpExpiresAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}