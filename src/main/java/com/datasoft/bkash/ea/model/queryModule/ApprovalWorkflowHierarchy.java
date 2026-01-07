package com.datasoft.bkash.ea.model.queryModule;

import lombok.Data;

@Data
public class ApprovalWorkflowHierarchy {
    private Integer id;
    private Integer approvalWorkflowId;
    private Integer division;
    private Integer department;
    private Integer teamId;
    private Integer userId;
    private Integer index;
    private String level;
    private Integer createdAt;
    private Integer createdBy;
    private Integer updatedAt;
    private Integer updatedBy;

}
