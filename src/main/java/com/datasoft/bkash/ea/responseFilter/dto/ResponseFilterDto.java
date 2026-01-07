package com.datasoft.bkash.ea.responseFilter.dto;

import com.datasoft.bkash.ea.model.ReportFilterCriteria;
import com.datasoft.bkash.ea.model.SearchCriteria;
import com.datasoft.bkash.ea.model.filter.FilterCriteria;
import com.datasoft.bkash.ea.model.queryModule.ConfigParams;
import com.datasoft.bkash.ea.model.queryModule.EnquiryAgainstReqDto;
import com.datasoft.bkash.ea.response.ApiResponse;
import com.datasoft.bkash.ea.responseFilter.ResponseFilterType;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFilterDto {
    String loginId;
    String param;
    String fileId;
    String fileExt;
    String generatedId;
    String requestUri;
    Integer userId;
    Integer eventId;
    Integer trainerId;
    Integer traineeId;
    Integer crIssueId;
    Object object;
    Map<String, String> map;
    Map<Object, Object> params;
    ResponseFilterType type;
    String searchParam;
    String orderParam;
    String orderType;
    Date startDate;
    Date endDate;
    FilterCriteria filterCriteria;
    ReportFilterCriteria reportFilterCriteria;
    ConfigParams configParams;
    Pageable pageable;
    List<EnquiryAgainstReqDto> enquiryAgainstReqDtos;
    ApiResponse apiResponse;
    ResponseEntity<?> responseEntity;
    SearchCriteria searchCriteria;
    HttpServletRequest servletRequest;
    Integer pcrIssueId;
    Integer trpId;
    String riskType;
    String filePath;
    String uploadSessionId;
    HttpServletResponse response;
    List<Map<String, Object>> paramList;
}
