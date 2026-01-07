package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationTransferDto {
    String recId;
    Integer recTypeId;
    Integer userId;
    Integer teamId;
    String remark;
    String transferType;
    String extraParam;
    Long objectId;
    String filePath;
    String fileName;
    Long fileSize;


}
