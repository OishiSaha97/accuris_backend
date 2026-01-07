package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryLogSaveDto {
    private Integer id;
    private String initiator_type;
    private Integer organizationTypeId;
    private String organizationType;
    private Integer teamId;
    private String team;
    private Integer intiator;
    private String initiatorName;
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
    private List<RemarkHistoryDto> remarkHistory;

    private List<WebsiteLinksDto> webLinks;
    private String queryStatus;
    private String respondStatus;
    private Boolean objStatus;
    private List<String> deleteFilePath;

    //SAVE or, SUBMIT or, UPDATE AND SUBMIT or, UPDATE
    private String action;
    private String investigationStatus;
    private String revertPhase;
}
