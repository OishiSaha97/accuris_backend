// SignupRequest.java
package com.datasoft.bkash.ea.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Metadata (set by controller)
    private String ip;
    private String userAgent;
}