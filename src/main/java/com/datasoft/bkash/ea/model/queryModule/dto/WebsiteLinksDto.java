package com.datasoft.bkash.ea.model.queryModule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebsiteLinksDto {
    private Integer id;
    private Integer queryId;
    private String title;
    private String links;
    private Boolean isResponder;
}
