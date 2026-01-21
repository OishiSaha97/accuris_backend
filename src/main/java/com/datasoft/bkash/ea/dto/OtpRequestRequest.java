package com.datasoft.bkash.ea.dto;

public class OtpRequestRequest {
    private String email;
    private String otpMethod;  // "EMAIL" or "PHONE"
    private String ip;
    private String userAgent;

    // Getters & Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOtpMethod() { return otpMethod; }
    public void setOtpMethod(String otpMethod) { this.otpMethod = otpMethod; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}
