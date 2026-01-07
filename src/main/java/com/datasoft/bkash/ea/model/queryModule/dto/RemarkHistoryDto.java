package com.datasoft.bkash.ea.model.queryModule.dto;

import com.datasoft.bkash.ea.model.common_properties.CommonProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemarkHistoryDto extends CommonProperties {
    private String queryLogId;
    private String objectType;
    private Boolean isMandatory;
    private String remark;
    private String module;
    private String bkashId;
    private String remarkDate;
    private String division;
    private String department;
}
