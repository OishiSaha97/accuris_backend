package com.datasoft.bkash.ea.dto;

import lombok.Data;

@Data
public class SignupEmailOtpRequest {
    private String primaryEmail;
    private String email2fa;
    private String ip;
    private String userAgent;

    // getters & setters
}
