package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionsummaryDto {
    private String queryId;
    private String photoId;
    private String accountNumber;
    private String tableJson;
    private String analysisType;
    private String contentType;
}
