package com.datasoft.bkash.ea.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;

@Data
public class IndividualCreditRequest {
    private String param;  // 'person info', 'location', 'financial info', 'final submit'
    @JsonProperty("pId")
    private Long pId;      // Individual ID (null for new records)
    private Long userId;   // User performing the operation
    private DataSet dataSet;

    @Data
    public static class DataSet {
        // Person Info fields
        private Long id;
        private String firstName;
        private String lastName;
        private String fatherName;
        private String motherName;
        private LocalDate dateOfBirth;
        private Integer genderId;
        private Integer maritalStatusId;
        private String phoneNumber;
        private String email;
        private String nationalIdPassportNo;
        private String idCopyUrl;

        // Location fields
        private String presentAddress;
        private String permanentAddress;
        private String city;
        private String stateProvince;
        private String postalCode;
        private String countryCode;

        // Financial Info fields
        private Long financialId;
        private Long individualsId;

        // Employment Information
        private Integer employerTypeId;
        private String employerName;
        private Integer employmentStatusId;
        private String jobDesignation;
        private Integer jobTenureYears;
        private Double monthlyGrossIncome;
        private Double monthlyNetIncome;

        // Business Information
        private String businessName;
        private Integer businessTypeId;
        private String industryType;
        private Integer yearsInBusiness;
        private Double monthlyBusinessIncome;

        // Financial and Credit Information
        private Double requestedLoanAmount;
        private Double downPaymentAmount;
        private Integer loanTenureMonths;
        private Integer repaymentPreferenceId;
        private String existingLoanDetails;
        private String creditCardDetails;

        // Banking
        private String bankName;
        private Integer activeBankAccounts;

        // Debt Information
        private Double totalOutstandingLoanAmount;
        private Double totalMonthlyEmi;
        private Double debtBurdenRatio;

        // Credit Assessment
        private Integer repaymentBehaviorId;
        private Integer cibStatusId;

        // ✅ ADDED: Security/Collateral & Risk Mitigation
        private Integer collateralAvailable;        // 1=Yes, 0=No
        private Integer collateralTypeId;           // 1=Property, 2=FDR
        private Double estimatedCollateralValue;
        private Integer guarantorAvailable;         // 1=Yes, 0=No
        private Integer coApplicantAvailable;       // 1=Yes, 0=No

        // ✅ FIXED: Arrays for mappings - simple integer arrays, not objects
        private Integer[] incomeTypeId;
        private Integer[] creditPurposeId;
    }

    @Data
    public static class IncomeType {
        private Integer id;
        private String name;
    }

    @Data
    public static class CreditPurpose {
        private Integer id;
        private String name;
    }
}
