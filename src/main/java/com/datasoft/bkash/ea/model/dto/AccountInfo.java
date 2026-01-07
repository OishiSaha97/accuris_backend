package com.datasoft.bkash.ea.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountInfo {
    private String accountNumber;
    private String accountType;
    private String photoId;
    private String photoIdType;
    private String name;
}
