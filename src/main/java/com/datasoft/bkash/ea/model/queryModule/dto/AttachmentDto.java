package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentDto {
    private Integer id;
    private Integer queryId;
    private String fileName;
    private String fileSize;
    private String filePath;
    private String fileType;
}
