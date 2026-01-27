package com.datasoft.bkash.ea.dto;

public class VerifySignupOtpRequest {
    private String primaryEmail;
    private String otp;
    private String ip;
    private String userAgent;

    // Constructors
    public VerifySignupOtpRequest() {
    }

    public VerifySignupOtpRequest(String primaryEmail, String otp) {
        this.primaryEmail = primaryEmail;
        this.otp = otp;
    }

    // Getters and Setters
    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}