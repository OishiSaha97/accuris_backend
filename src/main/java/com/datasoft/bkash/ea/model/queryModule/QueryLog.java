package com.datasoft.bkash.ea.model.queryModule;

import com.datasoft.bkash.ea.model.common_properties.CommonProperties;
import lombok.Data;

import java.util.Date;

@Data
/**
 * Initial model of QueryLog from scratch
 */
public class QueryLog extends CommonProperties {

    private Integer id;
    private String initiatorType;
    private Integer organizationTypeId; //FK (conf_query_organization_type_id.id)
    private String organizationType;
    private Integer initiator;
    private String initiatorName;
    private String department;
    private String division;
    private Integer teamId; //FK (conf_query_team_id.id)
    private String team;
    private String requestor;

    private String initiatorDetails;
    private String queryTitle;
    private Date queryDate;
    private String queryDetails;
    private String sourceReferenceNumber;
    private Date queryDeadline;
    private Integer priority;
    private String queryStatus;
    private String respondStatus;
    private Boolean objStatus;
    private String investigationStatus;
    private String investigationPhase;
    private String approvalStatus;

    private Date loggedAt;
    private Integer loggedBy;

    private String revertPhase;

    //Newly added fields for Team
    private String qlTeam;
    private String qlDivision;
    private String qlDepartment;
    private String ioTeam;
    private String ioDivision;
    private String ioDepartment;

}
