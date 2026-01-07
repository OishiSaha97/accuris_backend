package com.datasoft.bkash.ea.model.queryModule.dto;

import com.datasoft.bkash.ea.model.queryModule.QueryCommonReqDto;
import com.datasoft.bkash.ea.model.queryModule.SingleFilter;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class HistoryCheckDto {
    List<QueryCommonReqDto> queryCommonReqDtoList;
    String queryIds;
    String accountNumber;
    String photoId;
    String tin;
    String tradeLicense;
    String searchParam;
    String param;
    String allegedAcc;
    String extraParam;
    String otherParam;
    String startDate;
    String endDate;
    String startTime;
    String endTime;
    String queryString;
    String type;
    String transactionType;
    String resultIndx;
    String compare;
    List<SingleFilter> filterParam;
    String generatedId;
    Integer invIds;
    Integer paramLimit;
    Integer paramOffset;

}