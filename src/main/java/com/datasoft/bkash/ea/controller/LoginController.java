package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.dto.*;
import com.datasoft.bkash.ea.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {




    @Autowired
    private LoginService loginService;

    /**
     * PHASE 1: Login with email and password
     * Returns: SUCCESS if password is correct (no OTP sent yet)
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request,
                                               HttpServletRequest servletRequest) {
        // Capture IP and User-Agent
        request.setIp(getClientIp(servletRequest));
        request.setUserAgent(servletRequest.getHeader("User-Agent"));

        // Call LoginService - Phase 1 (Password verification only)
        LoginResponse response = loginService.loginWithPassword(request);

        // Return appropriate HTTP status based on login result
        switch (response.getStatus()) {
            case "PASSWORD_VERIFIED":
                return ResponseEntity.ok(response);  // 200 OK - Password correct, proceed to choose OTP method

            case "LOCKED":
                return ResponseEntity.status(HttpStatus.LOCKED)  // 423 Locked
                        .body(response);

            case "DISABLED":
            case "PENDING":
                return ResponseEntity.status(HttpStatus.FORBIDDEN)  // 403 Forbidden
                        .body(response);

            case "INVALID":
            default:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)  // 401 Unauthorized
                        .body(response);
        }
    }

    /**
     * PHASE 2: Request OTP (user chooses EMAIL or PHONE)
     * Returns: OTP_SENT status and sends OTP via chosen method
     */
    @PostMapping("/request-otp")
    public ResponseEntity<LoginResponse> requestOtp(@RequestBody OtpRequestRequest request,
                                                    HttpServletRequest servletRequest) {
        // Capture IP and User-Agent
        request.setIp(getClientIp(servletRequest));
        request.setUserAgent(servletRequest.getHeader("User-Agent"));

        // Call LoginService - Phase 2 (Generate and send OTP)
        LoginResponse response = loginService.requestOtp(request);

        // Return appropriate HTTP status
        if ("OTP_SENT".equals(response.getStatus())) {
            return ResponseEntity.ok(response);  // 200 OK - OTP sent
        } else if ("NO_EMAIL".equals(response.getStatus()) || "NO_PHONE".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)  // 412 Precondition Failed
                    .body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)  // 400 Bad Request
                    .body(response);
        }
    }

    /**
     * PHASE 3: Verify OTP
     * Returns: Session token if successful
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<LoginResponse> verifyOtp(@RequestBody OtpVerifyRequest request,
                                                   HttpServletRequest servletRequest) {
        // Capture IP and User-Agent
        request.setIp(getClientIp(servletRequest));
        request.setUserAgent(servletRequest.getHeader("User-Agent"));

        // Call LoginService - Phase 3 (OTP verification + Session creation)
        LoginResponse response = loginService.verifyOtp(request);

        // Return appropriate HTTP status
        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(response);  // 200 OK - Login complete
        } else if ("EXPIRED_OTP".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.GONE)  // 410 Gone
                    .body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)  // 401 Unauthorized
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

    @GetMapping("/testjonathon")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Auth controller is reachable!");
    }

    @GetMapping("/userid")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
        Long userId = loginService.getUserIdByEmail(email);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/username")
    public ResponseEntity<UserName> getUserNameById(@RequestParam Long id) {
        UserName userName = loginService.getUserNameById(id);
        return ResponseEntity.ok(userName);
    }


}