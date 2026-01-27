// SignupResponse.java
package com.datasoft.bkash.ea.dto;

import lombok.Data;

@Data
public class SignupResponse {
    private String status;  // SUCCESS, EMAIL_EXISTS, EMAIL_PENDING_VERIFICATION, INVALID_EMAIL, WEAK_PASSWORD, etc.
    private String message;
    private Long userId;  // User ID for reference
    private String email;  // Email for reference
}