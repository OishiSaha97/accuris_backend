package com.datasoft.bkash.ea.model.queryModule.dto;

import com.datasoft.bkash.ea.model.queryModule.ReportWriting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ReportWritingReqDto {

    // Approval Memo Save
    private String queryId;
    private String contentId;
    private Integer memoId;
    private String reportDepth;
    private String note;
    private String subjectName;
    private List<ReportWriting> invReportWritingList;
    private List<ReportWriting> reportWritingList;
    private List<String> accountNumbers;
    private String type;
    private String actionType;
    private String module;
    private Integer teamId;
    private Integer memoTypeId;
    private String department;
    private String division;
    private Integer priority;
    private String remark;
    private String team;
    private String memoType;
    private String aprMemoTempId;
    private Map<String,Object> params;

    // CR Report Save
    private Integer crId;
    private Integer crReportId;
    private Integer crTypeId;
    private Integer crIssueId;
    private String photoId;
    private String accountNumber;
    private String contentName;
    private String contentJson;
    private String analysisType;
    private String contentType;
    private String dataParam;
    private Integer createdBy;
    private Integer assignedTo;
    private String contentSelectDate;
    private String queryString;
    private String searchParam;
    private String filterParam;
    private String dataHeaders;
    private String dataKeys;
    private String crLogic;
    private String extraParam;
    private Integer psId;
    private String generatedId;
}
