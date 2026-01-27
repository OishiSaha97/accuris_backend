package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.dto.IndividualCreditRequest;
import com.datasoft.bkash.ea.dto.IndividualCreditResponse;
import com.datasoft.bkash.ea.service.IndividualCreditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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




    @Value("${app.upload.dir:/uploads}")  // Configurable in application.properties, default to /uploads
    private String uploadDir;

    @PostMapping("/upload/{individualId}/{fieldName}")
    public ResponseEntity<Map<String, String>> uploadDocument(
            @PathVariable Long individualId,
            @PathVariable String fieldName,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
        }

        try {
            // Create the directory path: /uploads/individuals/{individualId}
            Path individualDir = Paths.get(uploadDir + "/individuals/" + individualId);
            if (!Files.exists(individualDir)) {
                Files.createDirectories(individualDir);
            }

            // Get original file extension (e.g., .jpg, .pdf)
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // Construct the file name: {fieldName}.{ext}
            String storedFileName = individualId + "_" + fieldName + fileExtension;

            // Save the file
            Path filePath = individualDir.resolve(storedFileName);
            Files.copy(file.getInputStream(), filePath);

            // Relative path to return (adjust if your server serves from a different base)
            String relativePath = "/uploads/individuals/" + individualId + "/" + storedFileName;

            Map<String, String> response = new HashMap<>();
            response.put("path", relativePath);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to upload file"));
        }
    }

}