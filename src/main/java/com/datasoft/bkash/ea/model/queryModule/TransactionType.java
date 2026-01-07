package com.datasoft.bkash.ea.model.queryModule;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class TransactionType {
    private Integer id;
    private String ids;
    private String transactionTypeId;
    private String transactionTypeDesc;
    private String subTrxType;
    private String transactionType;
    private String createdAt;
    private Integer createdBy;
    private String updatedAt;
    private Integer updatedBy;
    private Boolean status;
    private String mappedStatus;
    private Boolean isDisplayDetails;
    private String param;
    private Remark remark;
}
