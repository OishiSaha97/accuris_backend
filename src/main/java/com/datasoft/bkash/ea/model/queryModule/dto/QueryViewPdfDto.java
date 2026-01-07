package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryViewPdfDto {
    private String initiatorInformationDto;
    private String queryDetailsDto;
    private List<WebsiteLinksDto> websiteLinksDtoList;
    private List<RemarkHistoryDto> remarkHistoryDtoList;
    private List<AttachmentDto> attachmentDtoList;
}
