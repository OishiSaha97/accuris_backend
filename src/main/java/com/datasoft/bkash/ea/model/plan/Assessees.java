package com.datasoft.bkash.ea.model.plan;

import lombok.Data;

import java.util.Date;

@Data
public class Assessees {

    private Integer id;
    private String businessPartnerType;
    private String accountNumber;

    private String agentType;
    private String agentName;

    private String daoName;

    private Integer dhId;
    private String dhName;
    private String dhMasterAcctNumber;
    private String dhAccountNumber;
    private String distributorHouse;

    private Integer dsoId;
    private String dsoName;
    private String dsoAccountNumber;
    private String dsoAcctNumber;

    private String merchantName;
    private String merchantType;
    private String maWalletNo;

    private String businessArea;
    private String businessRegion;

    private String masterAccountArea;
    private String masterAccountRegion;
    private String regionName;
    private String traineeRegion;
    private String divisionName;
    private String division;
    private String presentDivision;
    private String districtName;
    private String district;
    private String permanentDistrict;
    private String thanaName;
    private String permanentThana;

    private String shopName;
    private String shopAddress;
    private String presentAddress;
    private String permanentAddress;
    private Boolean accountStatus;
    private Boolean identityStatus;
    private Boolean operatorStatus;
    private Boolean tillStatus;
    private Boolean status;
    private Boolean discontinueStatus;
    private Boolean employeeStatus;

    private String entityType;
    private String ownershipType;
    private String ownerName;
    private String shopEmployeeName;
    private String operatorName;

    private Date shopEmployeeJoiningDate;
    private Date joiningDate;
    private Date registrationDate;
    private Date tlExpiryDate;
    private Date tradeLicenceEffectiveDate;

    private Integer geoCode;
    private String photoIdNumber;
    private String categoryCode;
    private String categoryName;
    private String organizationTin;

    private String tradeLicenseNo;
    private String vatRegistrationNo;
    private String businessType;

    private String photoTypeId;
    private String idNumber;
    private String tagReason;
    private String stateTag;
    private String employeeId;
    private String employeeName;
    private String designation;

    private String department;
    private String email;
    private String contactNumber;

    private String employeeType;
    private String employmentStatus;
    private String supervisorsName;
    private String supervisorsEmail;
    private String supervisorsContactNo;
    private String specialDesignation;
    private String gpsLatitude;
    private String gpsLongitude;
    private String accountRuleProfileId;
    private String accountRuleProfileDesc;

    private String channelName;
    private String identityRuleProfileId;
    private String identityRuleProfileDesc;
    private String legacyBankBranch;
    private String notificationReceivingMsisdn;
    private String registeringChannel;
    private String servingChannel;
    private String trainingFeedback;
    private String trainingStatus;
    private String trainingPlanStatus;
    private String traineeManagementFeedback;
    private String trainingRecommendation;

    private String planDate;
    private String planStatus;
}
