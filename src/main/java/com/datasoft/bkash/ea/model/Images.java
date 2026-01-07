package com.datasoft.bkash.ea.model;

import com.datasoft.bkash.ea.model.common_properties.CommonProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Images extends CommonProperties {

    private Integer id;
    private String checkSum;
    private String hash;
    private String imagePath;
    private String contentType;
    private Integer objectId;
    private String objectType;
    private String name;
    private String size;
    // Foreign keys
    private Integer userId;
}
