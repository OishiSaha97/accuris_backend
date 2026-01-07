package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryLogEditDto {
    private Integer id;
    private String initiator_type;
    private Integer organizationTypeId;
    private Integer teamId;
    private Integer intiator;
    private String requestor;
    private String division;
    private String department;
    private String intiatorDetails;
    private String queryTitle;
    private String queryDate;
    private String queryDetails;
    private String sourceReferenceNumber;
    private String queryDeadline;
    private Integer priority;
    private List<QueryTypeDto> queryType;
    private List<SourceTypeDto> sourceType;
    //    private List<AttachmentDto> attachment;
    private List<RemarkHistoryDto> remarkHistory;

    private List<WebsiteLinksDto> webLinks;
    private String queryStatus;
    private String respondStatus;
    private Boolean objStatus;
}
