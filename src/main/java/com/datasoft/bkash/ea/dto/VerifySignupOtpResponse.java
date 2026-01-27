package com.datasoft.bkash.ea.dto;

public class VerifySignupOtpResponse {
    private String status;
    private String message;
    private Long userId;

    // Constructors
    public VerifySignupOtpResponse() {
    }

    public VerifySignupOtpResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}