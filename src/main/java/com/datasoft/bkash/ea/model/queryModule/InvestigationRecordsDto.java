package com.datasoft.bkash.ea.model.queryModule;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
/**
 * This dto is for Investigation Record data
 */
public class InvestigationRecordsDto {
    public Integer id;
    public Integer queryId;
    public String queryIds;
    public Integer assignedTo;
    public String objStatus;
    public String startDate;
    public String endDate;
    public String createdAt;
    public Integer createdBy;
    public String remark;
    public String type;
    public String generatedId;
    public Map<String,Object> params;
}
