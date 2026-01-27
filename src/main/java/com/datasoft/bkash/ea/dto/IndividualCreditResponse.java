package com.datasoft.bkash.ea.dto;

import lombok.Data;

@Data
public class IndividualCreditResponse {
    private String status;           // SUCCESS or FAIL
    private String message;          // Message from SP
    private Long individualId;       // returnIndivId
    private Long financialInfoId;    // returnFinInfoId
}
