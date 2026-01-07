package com.datasoft.bkash.ea.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportFilterCriteria {

    private String id;
    private String traineeTypeId;
    private Integer traineeTypeCategoryId;
    private String traineeTypeCategoryName;
    private String trainingTypeId;
    private Integer trainingSourceId;
    private String subjectId;
    private String topicId;
    private String regionName;

}
