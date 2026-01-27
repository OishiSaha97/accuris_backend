package com.datasoft.bkash.ea.service;

import com.datasoft.bkash.ea.dto.SignupEmailOtpRequest;
import com.datasoft.bkash.ea.dto.SignupRequest;
import com.datasoft.bkash.ea.dto.SignupResponse;
import com.datasoft.bkash.ea.dto.VerifySignupOtpRequest;
import com.datasoft.bkash.ea.dto.VerifySignupOtpResponse;
import com.datasoft.bkash.ea.entity.User;
import com.datasoft.bkash.ea.repository.UserRepository;
import com.datasoft.bkash.ea.utils.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SmtpEmailService emailService;


    /**
     * PHASE 1: Register new user (no OTP sent)
     */
    @Transactional
    public SignupResponse registerUser(SignupRequest request) {
        SignupResponse response = new SignupResponse();

        // Validate required fields
        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty() ||
                request.getLastName() == null || request.getLastName().trim().isEmpty() ||
                request.getEmail() == null || request.getEmail().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            response.setStatus("MISSING_FIELDS");
            response.setMessage("First name, last name, email, and password are required");
            return response;
        }

        // Validate email format
        if (!isValidEmail(request.getEmail())) {
            response.setStatus("INVALID_EMAIL");
            response.setMessage("Invalid email format");
            return response;
        }

        // Validate password strength
        if (!isStrongPassword(request.getPassword())) {
            response.setStatus("WEAK_PASSWORD");
            response.setMessage("Password must be at least 8 characters with uppercase, lowercase, and numbers");
            return response;
        }

        // Normalize email
        String normalizedEmail = request.getEmail().toLowerCase().trim();

        // Check if email already exists with ACTIVE status
        Optional<User> existingUser = userRepository.findByEmailNormalized(normalizedEmail);
        if (existingUser.isPresent()) {
            if ("ACTIVE".equals(existingUser.get().getStatus())) {
                response.setStatus("EMAIL_EXISTS");
                response.setMessage("Email already registered and active. Please login.");
                return response;
            } else if ("PENDING".equals(existingUser.get().getStatus())) {
                response.setStatus("EMAIL_PENDING_VERIFICATION");
                response.setMessage("Email already registered but not verified. Please verify your account.");
                response.setUserId(existingUser.get().getId());
                response.setEmail(existingUser.get().getEmail());
                return response;
            }
        }

        // Create new user
        User user = new User();
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setEmail(request.getEmail().trim());
        user.setEmailNormalized(normalizedEmail);

        // email_2fa will be set in Phase 2 when provided
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setStatus("PENDING");
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setPasswordUpdatedAt(LocalDateTime.now());
        user.setFailedLoginCount(0);
        user.setOtp(null);
        user.setOtpExpiresAt(null);
        user.setCreatedAt(LocalDateTime.now());

        // Save user
        user = userRepository.save(user);

        response.setStatus("SUCCESS");
        response.setMessage("Registration successful. Please verify your email to activate your account.");
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());

        return response;
    }


    /**
     * PHASE 2: Request OTP for 2FA email
     */
    public SignupResponse requestEmailOtp(SignupEmailOtpRequest request) {

        // Validate 2FA email format
        if (!isValidEmail(request.getEmail2fa())) {
            SignupResponse response = new SignupResponse();
            response.setStatus("INVALID_EMAIL");
            response.setMessage("Invalid 2FA email format");
            return response;
        }

        // Find user by primary email and update email_2fa
        String normalizedPrimaryEmail = request.getPrimaryEmail().toLowerCase().trim();
        Optional<User> userOpt = userRepository.findByEmailNormalized(normalizedPrimaryEmail);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Set 2FA email (can be same or different from primary email)
            user.setEmail_2fa(request.getEmail2fa().trim());
            user.setEmail2faNormalized(request.getEmail2fa().toLowerCase().trim());

            userRepository.save(user);
        } else {
            // User not found - return generic message to prevent enumeration
            SignupResponse response = new SignupResponse();
            response.setStatus("USER_NOT_FOUND");
            response.setMessage("If the email is valid, a verification code has been sent");
            return response;
        }

        return jdbcTemplate.execute((ConnectionCallback<SignupResponse>) conn -> {
            try (CallableStatement cs =
                         conn.prepareCall("{call sp_cri_request_signup_otp(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

                cs.setString(1, request.getPrimaryEmail());
                cs.setString(2, request.getEmail2fa());
                cs.setString(3, request.getIp());
                cs.setString(4, request.getUserAgent());

                cs.registerOutParameter(5, Types.BIGINT);    // o_user_id
                cs.registerOutParameter(6, Types.VARCHAR);   // o_request_status
                cs.registerOutParameter(7, Types.VARCHAR);   // o_otp
                cs.registerOutParameter(8, Types.TIMESTAMP); // o_otp_expires_at
                cs.registerOutParameter(9, Types.VARCHAR);   // o_recipient

                cs.execute();

                SignupResponse response = new SignupResponse();
                response.setStatus(cs.getString(6));

                long uid = cs.getLong(5);
                response.setUserId(cs.wasNull() ? null : uid);

                if ("OTP_SENT".equals(response.getStatus())) {
                    sendSignupOtpEmail(cs.getString(9), cs.getString(7));
                    response.setMessage("Verification code sent to email");
                } else {
                    // Prevent email enumeration
                    response.setMessage(
                            "If the email is valid, a verification code has been sent");
                }

                return response;
            }
        });
    }


    /**
     * PHASE 3: Verify OTP and activate account
     */
    public VerifySignupOtpResponse verifySignupOtp(VerifySignupOtpRequest request) {

        // Validate required fields
        if (request.getPrimaryEmail() == null || request.getPrimaryEmail().trim().isEmpty() ||
                request.getOtp() == null || request.getOtp().trim().isEmpty()) {
            VerifySignupOtpResponse response = new VerifySignupOtpResponse();
            response.setStatus("MISSING_FIELDS");
            response.setMessage("Email and OTP are required");
            return response;
        }

        return jdbcTemplate.execute((ConnectionCallback<VerifySignupOtpResponse>) conn -> {
            try (CallableStatement cs =
                         conn.prepareCall("{call sp_cri_verify_signup_email_otp(?, ?, ?, ?, ?, ?)}")) {

                cs.setString(1, request.getPrimaryEmail());
                cs.setString(2, request.getOtp());
                cs.setString(3, request.getIp());
                cs.setString(4, request.getUserAgent());

                cs.registerOutParameter(5, Types.BIGINT);    // o_user_id
                cs.registerOutParameter(6, Types.VARCHAR);   // o_verify_status

                cs.execute();

                VerifySignupOtpResponse response = new VerifySignupOtpResponse();
                response.setStatus(cs.getString(6));

                long uid = cs.getLong(5);
                response.setUserId(cs.wasNull() ? null : uid);

                // Set appropriate messages based on status
                switch (response.getStatus()) {
                    case "SUCCESS":
                        response.setMessage("Email verified successfully. Your account is now active. Please login.");
                        break;
                    case "INVALID_OTP":
                        response.setMessage("Invalid verification code. Please try again.");
                        break;
                    case "EXPIRED_OTP":
                        response.setMessage("Verification code has expired. Please request a new one.");
                        break;
                    case "NO_USER":
                        response.setMessage("User not found. Please check your email and try again.");
                        break;
                    case "ALREADY_VERIFIED":
                        response.setMessage("Email already verified. Please login.");
                        break;
                    case "NOT_PENDING":
                        response.setMessage("Account is not in pending status. Please contact support.");
                        break;
                    case "NO_OTP":
                        response.setMessage("No verification code found. Please request a new one.");
                        break;
                    default:
                        response.setMessage("Verification failed. Please try again.");
                        break;
                }

                return response;
            }
        });
    }


    // Helper methods
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        return hasUpper && hasLower && hasDigit;
    }

    private void sendSignupOtpEmail(String email, String otp) {
        String subject = "Verify your email";
        String body =
                "<html><body>" +
                        "<h3>Email Verification</h3>" +
                        "<p>Your verification code is:</p>" +
                        "<h2>" + otp + "</h2>" +
                        "<p>This code expires in 5 minutes.</p>" +
                        "</body></html>";

        try {
            emailService.sendEmail(subject, body, new String[]{email}, null, null);
        } catch (Exception e) {
            System.err.println("Failed to send OTP email to " + email + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}