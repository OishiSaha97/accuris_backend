package com.datasoft.bkash.ea.model.queryModule;

import com.datasoft.bkash.ea.model.common_properties.CommonProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportWriting extends CommonProperties {
    private Integer id;
    private String queryId;
    private String memoId;
    private Integer crReportId;
    private String photoId;
    private String accountNumber;
    private String sectionName;
    private String contentName;
    private String content;
    private String plainText;
    private String contentType;
    private String contentId;
    private String dataParam;
    private String contentSelectDate;
    private Boolean sectionDeletable;
    private Integer sequenceNumber;
    private String requestFrom;
    private String module;
    private Integer crIssueId;
    private String analysisType;
}
