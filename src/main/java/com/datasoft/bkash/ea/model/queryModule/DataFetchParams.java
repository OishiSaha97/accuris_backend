package com.datasoft.bkash.ea.model.queryModule;

import lombok.Data;

@Data
public class DataFetchParams {
    private Integer pid;
    private String filterParam;
    private String searchParam;
    private String orderParam;
    private String orderType;
    private Integer paramLimit;
    private Integer paramOffset;
    private Boolean paramStatus;
}
