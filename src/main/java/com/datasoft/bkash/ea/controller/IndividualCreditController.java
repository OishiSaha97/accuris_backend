package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.dto.IndividualCreditRequest;
import com.datasoft.bkash.ea.dto.IndividualCreditResponse;
import com.datasoft.bkash.ea.service.IndividualCreditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/individual-credit-scoring-form")
@CrossOrigin(origins = "*")
public class IndividualCreditController {

    @Autowired
    private IndividualCreditService creditService;

    /**
     * Unified endpoint for individual credit scoring
     * POST /api/v1/credit-scoring/process
     *
     * Supports operations:
     * - 'person info'    : Save/update personal information
     * - 'location'       : Update location information
     * - 'financial info' : Save/update financial information
     * - 'final submit'   : Complete submission (all steps)
     */
    @PostMapping("/process")
    public ResponseEntity<IndividualCreditResponse> processCredit(@RequestBody String rawJson) {

        System.out.println("\n===== RAW JSON RECEIVED =====");
        System.out.println(rawJson);
        System.out.println("=============================\n");

        try {
            // Now map manually to DTO
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            IndividualCreditRequest request = mapper.readValue(rawJson, IndividualCreditRequest.class);

            IndividualCreditResponse response = creditService.processIndividualCredit(request);

            if ("SUCCESS".equalsIgnoreCase(response.getStatus())) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception e) {
            System.out.println("❌ EXCEPTION DURING DESERIALIZATION OR PROCESSING ❌");
            e.printStackTrace();

            IndividualCreditResponse errorResponse = new IndividualCreditResponse();
            errorResponse.setStatus("FAIL");
            errorResponse.setMessage("Server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}