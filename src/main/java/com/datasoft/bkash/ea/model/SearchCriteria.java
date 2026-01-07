package com.datasoft.bkash.ea.model;

import com.datasoft.bkash.ea.model.enums.AssesseeType;
import com.datasoft.bkash.ea.model.enums.AssessmentType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class SearchCriteria {
    private LocalDate fromDate;
    private LocalDate toDate;
    private AssesseeType assesseeType;
    private List<AssesseeType> assesseeNameList;
    private AssessmentType assessmentType;
    private Boolean isEdd;
    private Boolean isSarStr;
    private Boolean isImr;
    private Boolean hasEvidence;
    private String questionVersion;
    private String caoName;
    private List<String> caoNameList;
    private String designation;
    private String assessmentGrading;
    private String regionName;
    private List<String> regionNames;
    private Integer userId;
    private List<Integer> userIds;
    private String dhAccount;
    private List<String> dhAccounts;
    private Map<String, String> advancedCriteria;
    private Integer maxScore;
    private Integer minScore;
    private String accountNumber;
    private String assessmentUniqueIdentifier;
    private String assesseeName;


}
