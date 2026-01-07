package com.datasoft.bkash.ea.model.queryModule;

import lombok.Data;

import java.util.Date;

@Data
public class ApprovalWorkflowRequestType {
    private Integer id;
    private Integer approvalWorkflowId;
    private Integer approvalWorkflowTypeId;
    private Integer approvalWorkflowCategoryId;
    private String approvalWorkflowCategoryName;
    private Date createdAt;
    private Integer createdBy;
    private Date updatedAt;
    private Integer updatedBy;

}
