package com.datasoft.bkash.ea.model.queryModule.dto;

import com.datasoft.bkash.ea.model.queryModule.EnquiryAgainstReqDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailAddAccountReqDto {
    private String queryId;
    private List<EnquiryAgainstReqDto> enquiryAgainstReqDtos;
}
