package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.dto.SignupEmailOtpRequest;
import com.datasoft.bkash.ea.dto.SignupRequest;
import com.datasoft.bkash.ea.dto.SignupResponse;
import com.datasoft.bkash.ea.dto.VerifySignupOtpRequest;
import com.datasoft.bkash.ea.dto.VerifySignupOtpResponse;
import com.datasoft.bkash.ea.service.SignupService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SignupController {

    @Autowired
    private SignupService signupService;

    /**
     * PHASE 1: Register new user with basic information (no OTP sent)
     * Returns: SUCCESS after creating user record with PENDING status
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request,
                                                 HttpServletRequest servletRequest) {
        // Capture IP and User-Agent
        request.setIp(getClientIp(servletRequest));
        request.setUserAgent(servletRequest.getHeader("User-Agent"));

        // Call SignupService - Phase 1 (Create user without OTP)
        SignupResponse response = signupService.registerUser(request);

        // Return appropriate HTTP status based on signup result
        switch (response.getStatus()) {
            case "SUCCESS":
                return ResponseEntity.status(HttpStatus.CREATED)  // 201 Created
                        .body(response);

            case "EMAIL_EXISTS":
                return ResponseEntity.status(HttpStatus.CONFLICT)  // 409 Conflict
                        .body(response);

            case "EMAIL_PENDING_VERIFICATION":
                return ResponseEntity.status(HttpStatus.CONFLICT)  // 409 Conflict
                        .body(response);

            case "INVALID_EMAIL":
            case "WEAK_PASSWORD":
            case "MISSING_FIELDS":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)  // 400 Bad Request
                        .body(response);

            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)  // 500 Server Error
                        .body(response);
        }
    }

    /**
     * PHASE 2: Request OTP for email verification
     */
    @PostMapping("/signup/email-otp")
    public ResponseEntity<SignupResponse> requestEmailOtp(
            @RequestBody SignupEmailOtpRequest request,
            HttpServletRequest servletRequest) {

        // Capture IP and User-Agent from request headers
        request.setIp(getClientIp(servletRequest));
        request.setUserAgent(servletRequest.getHeader("User-Agent"));

        // Call SignupService (Phase 2) to generate & send OTP
        SignupResponse response = signupService.requestEmailOtp(request);

        // Always return 200 OK to avoid user enumeration
        return ResponseEntity.ok(response);
    }

    /**
     * PHASE 3: Verify OTP and activate account
     */
    @PostMapping("/signup/verify-otp")
    public ResponseEntity<VerifySignupOtpResponse> verifySignupOtp(
            @RequestBody VerifySignupOtpRequest request,
            HttpServletRequest servletRequest) {

        // Capture IP and User-Agent from request headers
        request.setIp(getClientIp(servletRequest));
        request.setUserAgent(servletRequest.getHeader("User-Agent"));

        // Call SignupService (Phase 3) to verify OTP
        VerifySignupOtpResponse response = signupService.verifySignupOtp(request);

        // Return appropriate HTTP status based on verification result
        switch (response.getStatus()) {
            case "SUCCESS":
                return ResponseEntity.ok(response);  // 200 OK

            case "ALREADY_VERIFIED":
                return ResponseEntity.status(HttpStatus.CONFLICT)  // 409 Conflict
                        .body(response);

            case "INVALID_OTP":
            case "EXPIRED_OTP":
            case "NO_OTP":
            case "MISSING_FIELDS":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)  // 400 Bad Request
                        .body(response);

            case "NO_USER":
            case "NOT_PENDING":
                return ResponseEntity.status(HttpStatus.NOT_FOUND)  // 404 Not Found
                        .body(response);

            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)  // 500 Server Error
                        .body(response);
        }
    }

    /**
     * Get real client IP, handling proxies and load balancers
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // If multiple IPs in X-Forwarded-For, take the first one
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }

    @GetMapping("/test-signup")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Signup controller is reachable!");
    }
}