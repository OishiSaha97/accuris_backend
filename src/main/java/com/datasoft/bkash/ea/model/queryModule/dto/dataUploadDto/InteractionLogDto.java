package com.datasoft.bkash.ea.model.queryModule.dto.dataUploadDto;

import lombok.Data;

@Data
public class InteractionLogDto {
    private String accountNumber;
    private String ticketLogDate;
    private String incidentDate;
    private String interactionLog;
    private String reason;
}
