package com.datasoft.bkash.ea.model.queryModule;

import lombok.Data;

import java.util.List;

@Data
public
class SingleFilter{
    private String prefix;
    private String key;
    private String compare;
    private String value;
    private String dataType;
    private String multiple;
    private String type;
    private List<String> valueList;
}
