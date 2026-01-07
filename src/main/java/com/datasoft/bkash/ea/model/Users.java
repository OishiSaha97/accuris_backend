package com.datasoft.bkash.ea.model;

import com.datasoft.bkash.ea.model.common_properties.CommonProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Users extends CommonProperties {
    private Integer id;
    private String ipNumber;
    private String designation;
    private String email;
    private String empId;
    private String imageUrl;
    private String loginId;
    private String menuItem;
    private String password;
    private String phoneNumber;
    private String roleParam;
    private String status;
    private String userName;
    private String userType;
    // Boolean fields
    private Boolean active;
    private Boolean iscao;
    // Foreign keys
    private Integer imageId;
}
