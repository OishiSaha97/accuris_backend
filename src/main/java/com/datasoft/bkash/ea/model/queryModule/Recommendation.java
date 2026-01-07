package com.datasoft.bkash.ea.model.queryModule;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class Recommendation {
    private String recommendationId;
    private String implementationStatus;
    private String implementationFeedback;
    private String implementationDate;
    private String implementationTime;
    private Integer rowNumber;
    private String invalidReason;
    private String listName;
    private Long requestId;
    private String filePath;
    private String fileName;
    private Long fileSize;

}
