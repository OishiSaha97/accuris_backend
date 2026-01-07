package com.datasoft.bkash.ea.model.queryModule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * For enquiry against add ,the request dto
 */
public class EnquiryAgainstReqDto {
    private String accountNumber;
    private String accountType;
    private String photoIdType;
    private String photoIdNumber;
    private String inputName;
    private String tinNumber;
    private String tradeLicence;
}
