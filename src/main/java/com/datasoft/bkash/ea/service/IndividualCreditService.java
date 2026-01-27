package com.datasoft.bkash.ea.service;

import com.datasoft.bkash.ea.dto.IndividualCreditRequest;
import com.datasoft.bkash.ea.dto.IndividualCreditResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Service
public class IndividualCreditService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    public IndividualCreditService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        // CRITICAL: Serialize dates as strings, not arrays
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Unified method to process individual credit scoring
     * Handles all operation types: 'person info', 'location', 'financial info', 'final submit'
     */
    public IndividualCreditResponse processIndividualCredit(IndividualCreditRequest request) {
        System.out.println("\n========================================");
        System.out.println("STARTING PROCESS INDIVIDUAL CREDIT");
        System.out.println("========================================");

        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call sp_cri_individual_credit_scoring(?, ?, ?, ?, ?, ?, ?, ?)}")) {

                // Validate param
                if (request.getParam() == null || request.getParam().trim().isEmpty()) {
                    System.out.println("❌ ERROR: param is null or empty");
                    IndividualCreditResponse errorResponse = new IndividualCreditResponse();
                    errorResponse.setStatus("FAIL");
                    errorResponse.setMessage("Parameter 'param' is required");
                    return errorResponse;
                }

                // Validate userId
                if (request.getUserId() == null) {
                    System.out.println("❌ ERROR: userId is null");
                    IndividualCreditResponse errorResponse = new IndividualCreditResponse();
                    errorResponse.setStatus("FAIL");
                    errorResponse.setMessage("User ID is required");
                    return errorResponse;
                }

                System.out.println("REQUEST RECEIVED:");
                System.out.println("  Param: " + request.getParam());
                System.out.println("  pId: " + request.getPId());
                System.out.println("  userId: " + request.getUserId());

                if (request.getDataSet() != null) {
                    System.out.println("DataSet received:");
                    System.out.println("  firstName: " + request.getDataSet().getFirstName());
                    System.out.println("  lastName: " + request.getDataSet().getLastName());
                    System.out.println("  dateOfBirth: " + request.getDataSet().getDateOfBirth());
                    System.out.println("  dateOfBirth class: " +
                            (request.getDataSet().getDateOfBirth() != null ?
                                    request.getDataSet().getDateOfBirth().getClass().getName() : "NULL"));
                }

                // Convert dataSet to JSON
                String jsonData = objectMapper.writeValueAsString(request.getDataSet());

                System.out.println("\nJSON SENT TO SP:");
                System.out.println(jsonData);
                System.out.println();

                // Set IN parameters
                System.out.println("Setting IN parameters:");
                if (request.getPId() != null) {
                    System.out.println("  pId: " + request.getPId() + " (LONG)");
                    cs.setLong(1, request.getPId());
                } else {
                    System.out.println("  pId: NULL");
                    cs.setNull(1, Types.INTEGER);
                }
                System.out.println("  dataSet: <JSON string>");
                cs.setString(2, jsonData);
                System.out.println("  param: " + request.getParam());
                cs.setString(3, request.getParam());
                System.out.println("  userId: " + request.getUserId());
                cs.setLong(4, request.getUserId());

                // Register OUT parameters
                System.out.println("\nRegistering OUT parameters:");
                System.out.println("  Parameter 5: outStatus (VARCHAR)");
                cs.registerOutParameter(5, Types.VARCHAR);  // outStatus
                System.out.println("  Parameter 6: message (VARCHAR)");
                cs.registerOutParameter(6, Types.VARCHAR);  // message
                System.out.println("  Parameter 7: returnIndivId (BIGINT)");
                cs.registerOutParameter(7, Types.BIGINT);   // returnIndivId
                System.out.println("  Parameter 8: returnFinInfoId (BIGINT)");
                cs.registerOutParameter(8, Types.BIGINT);   // returnFinInfoId

                // Execute the stored procedure
                System.out.println("\n🔄 Executing stored procedure...");
                cs.execute();
                System.out.println("✅ Stored procedure executed successfully");

                // Retrieve OUT parameters
                System.out.println("\nRetrieving OUT parameters:");

                String outStatus = cs.getString(5);
                System.out.println("  outStatus (param 5): '" + outStatus + "'");

                String message = cs.getString(6);
                System.out.println("  message (param 6): '" + message + "'");

                Long returnIndivId = cs.getLong(7);
                boolean indivIdWasNull = cs.wasNull();
                System.out.println("  returnIndivId (param 7): " + returnIndivId + " | wasNull: " + indivIdWasNull);

                Long returnFinInfoId = cs.getLong(8);
                boolean finInfoIdWasNull = cs.wasNull();
                System.out.println("  returnFinInfoId (param 8): " + returnFinInfoId + " | wasNull: " + finInfoIdWasNull);

                System.out.println("\n===== SP OUT PARAMETERS SUMMARY =====");
                System.out.println("outStatus: " + outStatus);
                System.out.println("message: " + message);
                System.out.println("returnIndivId: " + returnIndivId + " (wasNull: " + indivIdWasNull + ")");
                System.out.println("returnFinInfoId: " + returnFinInfoId + " (wasNull: " + finInfoIdWasNull + ")");
                System.out.println("=====================================");

                // Handle null values for IDs
                if (indivIdWasNull) {
                    System.out.println("⚠️  Setting returnIndivId to NULL (wasNull=true)");
                    returnIndivId = null;
                }
                if (finInfoIdWasNull) {
                    System.out.println("⚠️  Setting returnFinInfoId to NULL (wasNull=true)");
                    returnFinInfoId = null;
                }

                // Build response
                IndividualCreditResponse response = new IndividualCreditResponse();
                response.setStatus(outStatus != null ? outStatus : "FAIL");
                response.setMessage(message != null ? message : "Operation completed");
                response.setIndividualId(returnIndivId);
                response.setFinancialInfoId(returnFinInfoId);

                System.out.println("\n📦 RESPONSE OBJECT:");
                System.out.println("  status: " + response.getStatus());
                System.out.println("  message: " + response.getMessage());
                System.out.println("  individualId: " + response.getIndividualId());
                System.out.println("  financialInfoId: " + response.getFinancialInfoId());
                System.out.println("========================================\n");

                return response;

            } catch (Exception e) {
                System.out.println("\n❌❌❌ EXCEPTION OCCURRED ❌❌❌");
                System.out.println("Exception type: " + e.getClass().getName());
                System.out.println("Exception message: " + e.getMessage());
                e.printStackTrace();
                System.out.println("========================================\n");

                IndividualCreditResponse response = new IndividualCreditResponse();
                response.setStatus("FAIL");
                response.setMessage("Exception occurred: " + e.getMessage());
                return response;
            }
        });
    }
}