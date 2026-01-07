package com.datasoft.bkash.ea.model.queryModule;

import lombok.Data;

@Data
public class Remark {
    public Integer objectId;
    public String objectIds;
    public String objType;
    public String module;
    public String objRemark;
    public Integer createdBy;
    public String createdAt;
}
