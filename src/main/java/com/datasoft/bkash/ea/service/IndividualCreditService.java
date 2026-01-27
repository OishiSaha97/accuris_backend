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
}