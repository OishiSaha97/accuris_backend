package com.datasoft.bkash.ea.model.queryModule;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    private MultipartFile[] files;
    private String objType;
    private String objRemark;
    private Integer createdBy;
    private String createdAt;
}
