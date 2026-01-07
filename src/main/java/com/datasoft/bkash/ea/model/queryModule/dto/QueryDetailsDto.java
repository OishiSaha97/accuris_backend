package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryDetailsDto {
    private String queryTitle;
    private String queryDate;
    private String sourceReferenceNumber;
    private String queryDeadline;
    private String priority;
    private String queryDetails;
}
