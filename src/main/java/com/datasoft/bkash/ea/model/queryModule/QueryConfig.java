package com.datasoft.bkash.ea.model.queryModule;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryConfig {
    private Integer id;
    private String ids;
    private Integer organizationTypeId;
    private String objName; //recommendationType
    private String description;
    private String createdAt;
    private Integer createdBy;
    private String updatedAt;
    private Integer updatedBy;
    private Boolean objStatus;
    private Boolean obj_status;
    private String searchText;
    private Remark remark;
    private String remarks;
    private String division;
    private List<Map<String,Object>> teamList;
    private List<Map<String,Object>> divisionList;
    private String department;
    private List<Map<String,Object>> departmentList;
    private String initiatorType;
    private String initiatorCode;
    private Integer sla;
    private String team;
    private Integer teamId;
    private Integer userId;
    private Integer assignTo;
    private String flatRemark;
    private String notificationData;
    private String mnemonics;

}

