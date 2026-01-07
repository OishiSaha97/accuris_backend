package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountQueryReasonDto {
    String accountNumber;
    String accountType;
    String accountName;
    String queryType;
    String reason;
}
