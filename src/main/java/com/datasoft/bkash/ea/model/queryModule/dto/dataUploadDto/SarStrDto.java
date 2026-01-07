package com.datasoft.bkash.ea.model.queryModule.dto.dataUploadDto;

import lombok.Data;

@Data
public class SarStrDto {
    private String accountNumber;
    private String goAmlSubmissionDate;
    private String sourceDetails;
    private String incidentType;
    private String allegationType;
    private String sarStr;
    private String reason;
}
