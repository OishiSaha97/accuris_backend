package com.datasoft.bkash.ea.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EddReportDto {
    private String message;
    private Integer totalSuccess;
    private Integer totalRows;
    private Integer totalFails;
    private List<String> failList;
}
