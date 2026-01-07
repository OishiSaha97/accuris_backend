package com.datasoft.bkash.ea.model.queryModule.dto;

import com.datasoft.bkash.ea.model.common_properties.CommonProperties;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class UserTagTagTypeMapping extends CommonProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Transient
    private Integer userTagId;
    private Integer userTagTypeId;
    private Integer userId;
}
