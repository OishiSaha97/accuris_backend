package com.datasoft.bkash.ea.model.queryModule;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ApprovalWorkflow {
    private Integer id;
    private String name;
    private String status;
    private String description;
    private Integer moduleId;
    private Integer categoryId;
    private Integer criteriaId;
    private Integer division;
    private String divisionName;
    private Integer department;
    private String departmentName;
    private Integer teamId;
    private String teamName;
    private Integer userId;
    private String userName;
    private String identifier;
    private Date createdAt;
    private Integer createdBy;
    private Date updatedAt;
    private Integer updatedBy;
    private List<ApprovalWorkflowRequestType> requestTypes;
    private List<ApprovalWorkflowHierarchy> hierarchies;
    private String criteriaName;

    private String extraParam;

    public void generateIdentifier(){
        this.identifier = ""
                .concat(this.moduleId.toString())
                .concat(".")
                .concat(this.categoryId.toString())
                .concat(".")
                .concat(this.requestTypes
                        .stream()
                        .sorted(Comparator.comparing(ApprovalWorkflowRequestType::getApprovalWorkflowTypeId))
                        .map(ApprovalWorkflowRequestType::getApprovalWorkflowTypeId)
                        .collect(Collectors.toList())
                        .toString())
                .concat(".")
                .concat(this.criteriaId.toString())
                .concat(".");

        switch (this.criteriaId){
            case 2:
                this.identifier = this.identifier.concat(this.division.toString());
                this.criteriaName = this.divisionName;
                break;
            case 3:
                this.identifier = this.identifier.concat(this.department.toString());
                this.criteriaName = this.departmentName;
                break;
            case 4:
                this.identifier = this.identifier.concat(this.teamId.toString());
                this.criteriaName = this.teamName;
                break;
            case 5:
                this.identifier = this.identifier.concat(this.userId.toString());
                this.criteriaName = this.userName;
                break;
        }
    }


    public String getApproverListAsString(){
        ArrayList userList = new ArrayList<>();
        this.getHierarchies().forEach(hierarchies->{
            userList.add((hierarchies.getUserId()).toString());
        });
        return String.join(",",userList) ;
    }
}
