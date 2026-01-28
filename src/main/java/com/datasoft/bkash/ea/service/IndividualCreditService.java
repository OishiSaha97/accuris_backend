package com.datasoft.bkash.ea.service;

import com.datasoft.bkash.ea.dto.IndividualCreditRequest;
import com.datasoft.bkash.ea.dto.IndividualCreditResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndividualCreditService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    public IndividualCreditService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public IndividualCreditResponse processIndividualCredit(IndividualCreditRequest request) {
        return jdbcTemplate.execute((Connection conn) -> {
            // UPDATED: Now 7 parameters to match the new sp_save_full_individual_credit_scoring
            try (CallableStatement cs = conn.prepareCall(
                    "{call sp_cri_individual_credit_scoring(?, ?, ?, ?, ?, ?, ?)}")) {

                // 1. Convert dataSet to JSON string
                // The DB will extract the 'id' (pId) from inside this JSON
                String jsonData = objectMapper.writeValueAsString(request.getDataSet());

                // Set IN parameters
                // cs.set... (Index, Value)
                cs.setString(1, jsonData);          // IN dataSet
                cs.setString(2, request.getParam());  // IN param (SAVE or SUBMIT)
                cs.setLong(3, request.getUserId());   // IN userId

                // Register OUT parameters
                cs.registerOutParameter(4, Types.VARCHAR);  // outStatus
                cs.registerOutParameter(5, Types.VARCHAR);  // message
                cs.registerOutParameter(6, Types.BIGINT);   // returnIndivId
                cs.registerOutParameter(7, Types.BIGINT);   // returnFinInfoId

                // Execute
                cs.execute();

                // Retrieve Results
                String outStatus = cs.getString(4);
                String message = cs.getString(5);

                Long returnIndivId = cs.getLong(6);
                if (cs.wasNull()) returnIndivId = null;

                Long returnFinInfoId = cs.getLong(7);
                if (cs.wasNull()) returnFinInfoId = null;

                // Build Response
                IndividualCreditResponse response = new IndividualCreditResponse();
                response.setStatus(outStatus != null ? outStatus : "FAIL");
                response.setMessage(message != null ? message : "Operation completed");
                response.setIndividualId(returnIndivId);
                response.setFinancialInfoId(returnFinInfoId);

                return response;

            } catch (Exception e) {
                e.printStackTrace();
                IndividualCreditResponse response = new IndividualCreditResponse();
                response.setStatus("FAIL");
                response.setMessage("Exception occurred: " + e.getMessage());
                return response;
            }
        });
    }

    public Long getLatestSubmittedIndividualId(Long userId) {
        try {
            String sql = "SELECT id FROM individuals WHERE created_by = ? ORDER BY id DESC LIMIT 1";

            Long individualId = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> rs.getLong("id"),
                    userId);

            // Now check if this individual has submitted = 1
            if (individualId != null) {
                String checkSubmittedSql = "SELECT submitted FROM individuals WHERE id = ?";
                Integer submitted = jdbcTemplate.queryForObject(checkSubmittedSql,
                        (rs, rowNum) -> rs.getInt("submitted"),
                        individualId);

                // Return id only if submitted is 1, otherwise return null
                return (submitted != null && submitted == 0) ? individualId : null;
            }

            return null;

        } catch (Exception e) {
            // No individual found or any other error
            return null;
        }
    }

    public Map<String, Object> getIndividualById(Long individualId) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call sp_get_individual_data(?, ?, ?)}")) {

                // Set IN parameter
                cs.setLong(1, individualId);

                // Register OUT parameters
                cs.registerOutParameter(2, Types.VARCHAR);  // outStatus
                cs.registerOutParameter(3, Types.VARCHAR);  // outMessage

                // Execute
                boolean hasResults = cs.execute();

                // Get OUT parameters
                String outStatus = cs.getString(2);
                String outMessage = cs.getString(3);

                if (!"SUCCESS".equals(outStatus)) {
                    return null;
                }

                Map<String, Object> result = new HashMap<>();

                // Result Set 1: Personal Information
                if (hasResults) {
                    try (ResultSet rs1 = cs.getResultSet()) {
                        if (rs1.next()) {
                            Map<String, Object> personal = new HashMap<>();
                            personal.put("id", rs1.getLong("id"));
                            personal.put("firstName", rs1.getString("firstName"));
                            personal.put("lastName", rs1.getString("lastName"));
                            personal.put("fathersName", rs1.getString("fathersName"));
                            personal.put("mothersName", rs1.getString("mothersName"));
                            personal.put("dateOfBirth", rs1.getDate("dateOfBirth"));

                            // Gender with lookup
                            Map<String, Object> gender = new HashMap<>();
                            gender.put("id", rs1.getInt("genderId"));
                            gender.put("name", rs1.getString("genderName"));
                            personal.put("gender", gender);

                            // Marital Status with lookup
                            Map<String, Object> maritalStatus = new HashMap<>();
                            maritalStatus.put("id", rs1.getInt("maritalStatusId"));
                            maritalStatus.put("maritalStatus", rs1.getString("maritalStatusName"));
                            personal.put("maritalStatus", maritalStatus);

                            personal.put("idNumber", rs1.getString("idNumber"));
                            personal.put("phoneNumber", rs1.getString("phoneNumber"));
                            personal.put("email", rs1.getString("email"));
                            personal.put("uploadId", rs1.getString("uploadId"));

                            result.put("personal", personal);
                        }
                    }
                }

                // Result Set 2: Location Information
                if (cs.getMoreResults()) {
                    try (ResultSet rs2 = cs.getResultSet()) {
                        if (rs2.next()) {
                            Map<String, Object> location = new HashMap<>();
                            location.put("presentAddress", rs2.getString("presentAddress"));
                            location.put("permanentAddress", rs2.getString("permanentAddress"));
                            location.put("city", rs2.getString("city"));
                            location.put("stateOrDistrict", rs2.getString("stateOrDistrict"));
                            location.put("postalCode", rs2.getString("postalCode"));
                            location.put("country", rs2.getString("country"));
                            location.put("lengthOfStay", rs2.getInt("lengthOfStay"));

                            result.put("location", location);
                        }
                    }
                }

                // Result Set 3: Financial Information
                Map<String, Object> financial = new HashMap<>();
                if (cs.getMoreResults()) {
                    try (ResultSet rs3 = cs.getResultSet()) {
                        if (rs3.next()) {
                            financial.put("financialId", rs3.getLong("financialId"));

                            // Employer Info
                            Map<String, Object> employerInfo = new HashMap<>();

                            Map<String, Object> employerType = new HashMap<>();
                            employerType.put("id", rs3.getInt("employerTypeId"));
                            employerType.put("name", rs3.getString("employerTypeName"));
                            employerInfo.put("employerType", employerType);

                            employerInfo.put("employerName", rs3.getString("employerName"));

                            Map<String, Object> employmentStatus = new HashMap<>();
                            employmentStatus.put("id", rs3.getInt("employmentStatusId"));
                            employmentStatus.put("name", rs3.getString("employmentStatusName"));
                            employerInfo.put("employmentStatus", employmentStatus);

                            employerInfo.put("jobDesignation", rs3.getString("jobDesignation"));
                            employerInfo.put("jobTenureYears", rs3.getInt("jobTenureYears"));
                            employerInfo.put("monthlyGrossIncome", rs3.getBigDecimal("monthlyGrossIncome"));
                            employerInfo.put("monthlyNetIncome", rs3.getBigDecimal("monthlyNetIncome"));

                            financial.put("employerInfo", employerInfo);

                            // Business Info
                            Map<String, Object> businessInfo = new HashMap<>();
                            businessInfo.put("businessName", rs3.getString("businessName"));

                            Map<String, Object> businessType = new HashMap<>();
                            businessType.put("id", rs3.getInt("businessTypeId"));
                            businessType.put("name", rs3.getString("businessTypeName"));
                            businessInfo.put("businessType", businessType);

                            businessInfo.put("industryType", rs3.getString("industryType"));
                            businessInfo.put("yearsInBusiness", rs3.getInt("yearsInBusiness"));
                            businessInfo.put("monthlyBusinessIncome", rs3.getBigDecimal("monthlyBusinessIncome"));

                            financial.put("businessInfo", businessInfo);

                            // Credit Info
                            Map<String, Object> creditInfo = new HashMap<>();
                            creditInfo.put("requestedLoanAmount", rs3.getBigDecimal("requestedLoanAmount"));
                            creditInfo.put("downPaymentAmount", rs3.getBigDecimal("downPaymentAmount"));
                            creditInfo.put("loanTenureMonths", rs3.getInt("loanTenureMonths"));

                            Map<String, Object> repaymentPreference = new HashMap<>();
                            repaymentPreference.put("id", rs3.getInt("repaymentPreferenceId"));
                            repaymentPreference.put("name", rs3.getString("repaymentPreferenceName"));
                            creditInfo.put("repaymentPreference", repaymentPreference);

                            creditInfo.put("existingLoanDetails", rs3.getString("existingLoanDetails"));
                            creditInfo.put("creditCardDetails", rs3.getString("creditCardDetails"));

                            financial.put("creditInfo", creditInfo);

                            // Security Info
                            Map<String, Object> securityInfo = new HashMap<>();
                            securityInfo.put("collateralAvailable", rs3.getInt("collateralAvailable"));

                            Map<String, Object> collateralType = new HashMap<>();
                            collateralType.put("id", rs3.getInt("collateralTypeId"));
                            collateralType.put("name", rs3.getString("collateralTypeName"));
                            securityInfo.put("collateralType", collateralType);

                            securityInfo.put("estimatedCollateralValue", rs3.getBigDecimal("estimatedCollateralValue"));
                            securityInfo.put("guarantorAvailable", rs3.getInt("guarantorAvailable"));
                            securityInfo.put("coApplicantAvailable", rs3.getInt("coApplicantAvailable"));

                            financial.put("securityInfo", securityInfo);
                        }
                    }
                }

                // Result Set 4: Credit Purpose (Multi-select)
                Map<String, Object> basicInfo = new HashMap<>();
                List<Map<String, Object>> creditPurposeList = new ArrayList<>();
                if (cs.getMoreResults()) {
                    try (ResultSet rs4 = cs.getResultSet()) {
                        while (rs4.next()) {
                            Map<String, Object> cp = new HashMap<>();
                            cp.put("id", rs4.getInt("id"));
                            cp.put("name", rs4.getString("name"));
                            creditPurposeList.add(cp);
                        }
                    }
                }
                basicInfo.put("creditPurpose", creditPurposeList);

                // Result Set 5: Income Type (Multi-select)
                List<Map<String, Object>> incomeTypeList = new ArrayList<>();
                if (cs.getMoreResults()) {
                    try (ResultSet rs5 = cs.getResultSet()) {
                        while (rs5.next()) {
                            Map<String, Object> it = new HashMap<>();
                            it.put("id", rs5.getInt("id"));
                            it.put("name", rs5.getString("name"));
                            incomeTypeList.add(it);
                        }
                    }
                }
                basicInfo.put("incomeType", incomeTypeList);

                financial.put("basicInfo", basicInfo);
                result.put("financial", financial);

                // Result Set 6: Uploads
                if (cs.getMoreResults()) {
                    try (ResultSet rs6 = cs.getResultSet()) {
                        if (rs6.next()) {
                            Map<String, Object> uploads = new HashMap<>();
                            uploads.put("idCopy", rs6.getString("idCopy"));
                            uploads.put("idCopyFilename", rs6.getString("idCopyFilename"));
                            uploads.put("photograph", rs6.getString("photograph"));
                            uploads.put("photographFilename", rs6.getString("photographFilename"));
                            uploads.put("salaryCertificate", rs6.getString("salaryCertificate"));
                            uploads.put("salaryCertificateFilename", rs6.getString("salaryCertificateFilename"));
                            uploads.put("bankStatement", rs6.getString("bankStatement"));
                            uploads.put("bankStatementFilename", rs6.getString("bankStatementFilename"));
                            uploads.put("incomeTaxReturn", rs6.getString("incomeTaxReturn"));
                            uploads.put("incomeTaxReturnFilename", rs6.getString("incomeTaxReturnFilename"));
                            uploads.put("cibConsentForm", rs6.getString("cibConsentForm"));
                            uploads.put("cibConsentFormFilename", rs6.getString("cibConsentFormFilename"));

                            result.put("uploads", uploads);
                        }
                    }
                }

                // Result Set 7: Metadata
                if (cs.getMoreResults()) {
                    try (ResultSet rs7 = cs.getResultSet()) {
                        if (rs7.next()) {
                            Map<String, Object> metadata = new HashMap<>();
                            metadata.put("individualId", rs7.getLong("individualId"));
                            metadata.put("submitted", rs7.getInt("submitted"));
                            metadata.put("idType", rs7.getString("idType"));
                            metadata.put("idIssueDate", rs7.getDate("idIssueDate"));

                            result.put("metadata", metadata);
                        }
                    }
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }



    public Map<String, List<Map<String, Object>>> getAllConfigurations() {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call sp_get_all_configurations()}")) {

                Map<String, List<Map<String, Object>>> configurations = new HashMap<>();

                // Execute
                boolean hasResults = cs.execute();

                // Result Set 1: Genders
                if (hasResults) {
                    List<Map<String, Object>> genders = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> gender = new HashMap<>();
                            gender.put("id", rs.getInt("id"));
                            gender.put("name", rs.getString("name"));
                            genders.add(gender);
                        }
                    }
                    configurations.put("genders", genders);
                }

                // Result Set 2: Marital Statuses
                if (cs.getMoreResults()) {
                    List<Map<String, Object>> maritalStatuses = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> status = new HashMap<>();
                            status.put("id", rs.getInt("id"));
                            status.put("maritalStatus", rs.getString("marital_status"));
                            maritalStatuses.add(status);
                        }
                    }
                    configurations.put("maritalStatuses", maritalStatuses);
                }

                // Result Set 3: Employer Types
                if (cs.getMoreResults()) {
                    List<Map<String, Object>> employerTypes = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> type = new HashMap<>();
                            type.put("id", rs.getInt("id"));
                            type.put("name", rs.getString("name"));
                            employerTypes.add(type);
                        }
                    }
                    configurations.put("employerTypes", employerTypes);
                }

                // Result Set 4: Employment Statuses
                if (cs.getMoreResults()) {
                    List<Map<String, Object>> employmentStatuses = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> status = new HashMap<>();
                            status.put("id", rs.getInt("id"));
                            status.put("employmentStatus", rs.getString("employment_status"));
                            employmentStatuses.add(status);
                        }
                    }
                    configurations.put("employmentStatuses", employmentStatuses);
                }

                // Result Set 5: Business Types
                if (cs.getMoreResults()) {
                    List<Map<String, Object>> businessTypes = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> type = new HashMap<>();
                            type.put("id", rs.getInt("id"));
                            type.put("name", rs.getString("name"));
                            businessTypes.add(type);
                        }
                    }
                    configurations.put("businessTypes", businessTypes);
                }

                // Result Set 6: Credit Purposes
                if (cs.getMoreResults()) {
                    List<Map<String, Object>> creditPurposes = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> purpose = new HashMap<>();
                            purpose.put("id", rs.getInt("id"));
                            purpose.put("name", rs.getString("name"));
                            creditPurposes.add(purpose);
                        }
                    }
                    configurations.put("creditPurposes", creditPurposes);
                }

                // Result Set 7: Income Types
                if (cs.getMoreResults()) {
                    List<Map<String, Object>> incomeTypes = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> type = new HashMap<>();
                            type.put("id", rs.getInt("id"));
                            type.put("incomeType", rs.getString("income_type"));
                            incomeTypes.add(type);
                        }
                    }
                    configurations.put("incomeTypes", incomeTypes);
                }

                // Result Set 8: Repayment Preferences
                if (cs.getMoreResults()) {
                    List<Map<String, Object>> repaymentPreferences = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> pref = new HashMap<>();
                            pref.put("id", rs.getInt("id"));
                            pref.put("name", rs.getString("name"));
                            repaymentPreferences.add(pref);
                        }
                    }
                    configurations.put("repaymentPreferences", repaymentPreferences);
                }

                // Result Set 9: Collateral Types
                if (cs.getMoreResults()) {
                    List<Map<String, Object>> collateralTypes = new ArrayList<>();
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            Map<String, Object> type = new HashMap<>();
                            type.put("id", rs.getInt("id"));
                            type.put("name", rs.getString("name"));
                            collateralTypes.add(type);
                        }
                    }
                    configurations.put("collateralTypes", collateralTypes);
                }

                return configurations;

            } catch (Exception e) {
                e.printStackTrace();
                return new HashMap<>();
            }
        });
    }

}