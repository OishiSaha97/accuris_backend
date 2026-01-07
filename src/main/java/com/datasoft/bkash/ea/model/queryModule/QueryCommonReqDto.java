package com.datasoft.bkash.ea.model.queryModule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * This dto is common for every SP call
 */
public class QueryCommonReqDto {
    public Integer id;
    public String param;
    public Boolean obj_status;
    public String obj_name;
    public String division;
    public String department;
    public Integer organization_type_id;
    public String source_ref_number;
    public String account_number;
    public String intiator;
}
