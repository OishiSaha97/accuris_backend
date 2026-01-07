package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestigationMonitor {
    private String id;
    private String investigation_id;
    private Long allegation_type;
    private String report_depth;
    private String current_io_name;
    private Long io_history;
    private String intiator;
    private String submitted_by;
    private String submitted_date;
    private String request_from;
    private String investigation_start_date;
    private String priority_flag;
    private String investigation_status;
    private String approval_status;
    private String implementation_status;
    private String sla;
    private String investigation_deadline;
    private String investigation_duration;
    private String query_deadline;
}
