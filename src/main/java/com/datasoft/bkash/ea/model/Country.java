package com.datasoft.bkash.ea.model;

import com.datasoft.bkash.ea.model.common_properties.CommonProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Country extends CommonProperties {

    private Integer id;
    private String name;

}
