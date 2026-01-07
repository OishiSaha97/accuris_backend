package com.datasoft.bkash.ea.model.queryModule.dto.dataUploadDto;

import lombok.Data;

@Data
public class MediaScreeningDto {
    private String screeningDate;
    private String source;
    private String pageNameOrNewspaperName;
    private String suspiciousOrRelevantCasesFoundOrNotFound;
    private String newsType;
    private String newsTitleOrPageTitleOrVideoTitle;
    private String webLink;
    private String requiredScreeningTimeInMinutes;
    private String memoPrepared;
    private String memoNo;
    private String forwardedToRelevantTeam;
    private String screenedBy;
    private String otherInfo;
    private String cellNumbersFoundDuringScreening;
    private String screeningTime;

}
