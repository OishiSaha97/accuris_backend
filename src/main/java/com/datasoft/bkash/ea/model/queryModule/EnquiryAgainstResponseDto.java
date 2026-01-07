package com.datasoft.bkash.ea.model.queryModule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Response dto for enquiry against, no usages
 */
public class EnquiryAgainstResponseDto {
    private String accountNumber;
    private String accountType;
    private String photoIdType;
    private String photoIdNumber;
    private String photoIdVerificationStatus;
    private String kycQcStatus;
    private String entityName;
    private String personName;
    private String tinNumber;
    private String tradeLicenceNumber;
}
