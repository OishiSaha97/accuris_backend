package com.datasoft.bkash.ea.model.queryModule.dto.dataUploadDto;

import lombok.Data;

@Data
public class CommercialDiscontinuationDto {
    private String distributor;
    private String accountNumber;
    private String discontinuationDate;
    private String discontinuationReason;
    private String discontinuationSubReason;
}
