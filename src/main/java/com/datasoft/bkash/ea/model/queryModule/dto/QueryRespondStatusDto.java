package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class QueryRespondStatusDto {
    @NotNull
    private List<Integer> queryLogId;
    @NotEmpty
    private String respondStatus;
    private RemarkHistoryDto remarkHistory;
}
