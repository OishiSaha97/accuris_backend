package com.datasoft.bkash.ea.batch.processor;

import com.datasoft.bkash.ea.config.SftpConfig;
import com.datasoft.bkash.ea.dao.JdbcFunctionDao;
import com.datasoft.bkash.ea.model.queryModule.ConfigParams;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class CsvProcessorRemote {

    private final JdbcFunctionDao jdbcFunctionDao;
    private final ConfigParams configParams;
    private List<Map<String, Object>> results = new ArrayList<>();
    private Map<String, Object> spParams;
    private final String procedureName;
    public final List<String> files = new ArrayList<>();
    private final Integer userId;
    private final Integer batchSize;
    private final Integer chunkMultiplicationSize;
    private Integer currentRow = 0;
    private final String baseDir;
    boolean isHeaderSet = false;
    String currentFile = "";
    List<String> headers = new ArrayList<>();
    List<String> columnHeaders = new ArrayList<>();
    private String procedureSrc="";

    @Autowired
    private SftpConfig sftpConfig;


    public CsvProcessorRemote(JdbcFunctionDao jdbcFunctionDao, String procedureName, Integer batchSize, Integer chunkMultiplicationSize, String baseDir, ConfigParams configParams, Integer userId, Map<String, Object> spParams,String procedureSrc){
        this.jdbcFunctionDao = jdbcFunctionDao;
        this.procedureName = procedureName;
        this.batchSize = batchSize;
        this.chunkMultiplicationSize = chunkMultiplicationSize;
        this.baseDir = baseDir;
        this.configParams = configParams;
        this.spParams = spParams;
        this.userId = userId;
        this.procedureSrc=procedureSrc;

    }


    public String executeBatch(SftpConfig sftpConfig){

        while(true){
            Map<String,Object> inParam=this.spParams;
            inParam.put("paramLimit", batchSize);
            inParam.put("paramOffset", currentRow);
            try{
                HashMap<String, Object> resultMap = new HashMap<>();
                Map<String,Object> procedureResult;
                if(Objects.equals(procedureSrc, "slave")){
                    procedureResult = jdbcFunctionDao.getProcedureResult(procedureName, inParam);
                }else{
                    procedureResult = jdbcFunctionDao.getProcedureResult(procedureName, inParam);
                }

                String result;

                if(procedureResult.containsKey("sequenceOfList") && !procedureResult.get("sequenceOfList").toString().isEmpty()) {
                    if (!procedureResult.get("sequenceOfList").toString().isEmpty()) {
                        result = procedureResult.get("sequenceOfList").toString();
                        String[] resultSize = result.split("~");
                        for (int i = 0; i < resultSize.length; i++) {
                            resultMap.put(resultSize[i], procedureResult.get("#result-set-" + (i + 1)));
                        }
                    }
                }else{
                    resultMap.put("data", procedureResult.get("#result-set-1"));
                }

                if(!isHeaderSet){
                    if(procedureResult.containsKey("sequenceOfList") && procedureResult.containsKey("headerColumn")) {
                        headers = Arrays.asList(((String) procedureResult.get("headerColumn")).split(","));
                        columnHeaders = Arrays.asList(((String) procedureResult.get("keySet")).split(","));
                    } else if (procedureResult.containsKey("sequenceOfList")) {
                        headers = ((List<Map<String, Object>>) resultMap.get("headers")).stream().map(map -> map.get("visibleValue").toString()).collect(Collectors.toList());
                        columnHeaders = ((List<Map<String, Object>>) resultMap.get("headers")).stream().map(map -> map.get("actualValue").toString()).collect(Collectors.toList());
                    }else if( Objects.equals(this.procedureSrc, "Unmapped Product Type List") || Objects.equals(this.procedureSrc, "Unmapped Account To Entity Type List") || Objects.equals(this.procedureSrc, "Unmapped Account To Business Type List")){
                        String headerJSON = (String) procedureResult.get("headerSet");
                        ObjectMapper objectMapper = new ObjectMapper();

                        JsonNode headerArray = objectMapper.readTree(headerJSON);
                        List<String> header = new ArrayList<>();

                        for (JsonNode obj : headerArray) {
                            header.add(obj.get("name").asText());
                        }
                        String columnJSON = (String) procedureResult.get("keySet");
                        JsonNode columnArray = objectMapper.readTree(columnJSON);
                        List<String> columnHeader = new ArrayList<>();

                        for (JsonNode obj : columnArray) {
                            columnHeader.add(obj.get("name").asText());
                        }
                        headers=header;
                        columnHeaders=columnHeader;
                    }else{
                        headers=Arrays.asList(((String)procedureResult.get("headerColumn")).split(","));
                        columnHeaders=Arrays.asList(((String)procedureResult.get("keySet")).split(","));
                    }
                    isHeaderSet = true;
                }
                results = (List<Map<String, Object>>) resultMap.get("data");

                if(results.isEmpty() && currentFile.isEmpty()){
                    this.makeCSV(sftpConfig);
                    break;
                }else if(results.isEmpty()){
                    break;
                }
                this.makeCSV(sftpConfig);
            } catch (Exception e){
                log.error(e.getMessage(), e.getCause());
                break;
            }
        }
        return currentFile;
    }


    private void makeCSV(SftpConfig sftpConfig) throws IOException, JSchException, SftpException {
        currentFile = UUID.randomUUID().toString().replace("-", "").toUpperCase().concat(".csv");

        JSch jsch = new JSch();
        Session session = jsch.getSession(sftpConfig.getSftpUsername(), sftpConfig.getSftpHost(), sftpConfig.getSftpPort());
        session.setPassword(sftpConfig.getSftpPassword());

        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
        sftpChannel.connect();

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            OutputStreamWriter csvWriter = new OutputStreamWriter(outputStream);


            StringBuilder csvHeader = new StringBuilder();
            for(String header : headers){
                csvHeader.append("\"").append(header).append("\"").append(",");
            }
            csvWriter.append(csvHeader.toString());
            csvWriter.append("\n");

            boolean shouldIterationContinue=true;
                for (int i = 0; i < chunkMultiplicationSize; i++) {
                    if (i > 0 && Objects.equals(shouldIterationContinue, true)) {
                        this.fetchData();
                        if (results.size() > 0) {
                            shouldIterationContinue = true;
                        } else {
                            shouldIterationContinue = false;
                        }
                        if (results.size() > 0) {
                            for (Map<String, Object> result : results) {
                                for (String header : columnHeaders) {
                                    csvWriter.append("\"").append((Objects.nonNull(result.get(header)) ? result.get(header).toString() : "")).append("\"").append(",");
                                }
                                csvWriter.append("\n");
                            }
                            currentRow += results.size();
                            results.clear();
                        }
                    } else {
                        if (results.size() > 0) {
                            shouldIterationContinue = true;
                        } else {
                            shouldIterationContinue = false;
                        }
                        if (Objects.equals(shouldIterationContinue, true)) {
                            for (Map<String, Object> result : results) {
                                for (String header : columnHeaders) {
                                    csvWriter.append("\"").append((Objects.nonNull(result.get(header)) ? result.get(header).toString() : "")).append("\"").append(",");
                                }
                                csvWriter.append("\n");
                            }
                            currentRow += results.size();
                            results.clear();
                        }
                    }
                }

            csvWriter.flush();

            // Upload the in-memory content to SFTP
            sftpChannel.put(new ByteArrayInputStream(outputStream.toByteArray()), baseDir + currentFile);

            files.add(currentFile);

        } finally {
            sftpChannel.disconnect();
            session.disconnect();
        }
    }

    public void fetchData(){
        Map<String,Object> inParam=this.spParams;
        inParam.put("paramLimit", batchSize);
        inParam.put("paramOffset", currentRow);
        try{
            HashMap<String, Object> resultMap = new HashMap<>();
            Map<String,Object> procedureResult;
            if(Objects.equals(procedureSrc, "slave")){
                procedureResult = jdbcFunctionDao.getProcedureResult(procedureName, inParam);
            }else{
                procedureResult = jdbcFunctionDao.getProcedureResult(procedureName, inParam);
            }
            String result;

            if(procedureResult.containsKey("sequenceOfList")) {
                if (!procedureResult.get("sequenceOfList").toString().isEmpty()) {
                    result = procedureResult.get("sequenceOfList").toString();
                    String[] resultSize = result.split("~");
                    for (int i = 0; i < resultSize.length; i++) {
                        resultMap.put(resultSize[i], procedureResult.get("#result-set-" + (i + 1)));
                    }
                }
            }else{
                resultMap.put("data", procedureResult.get("#result-set-1"));
            }

            if(procedureResult.containsKey("sequenceOfList")) {
                headers = ((List<Map<String, Object>>) resultMap.get("headers")).stream().map(map -> map.get("visibleValue").toString()).collect(Collectors.toList());
                columnHeaders = ((List<Map<String, Object>>) resultMap.get("headers")).stream().map(map -> map.get("actualValue").toString()).collect(Collectors.toList());
            }else{
                headers=Arrays.asList(((String)procedureResult.get("headerColumn")).split(","));
                columnHeaders=Arrays.asList(((String)procedureResult.get("keySet")).split(","));
            }
            results = (List<Map<String, Object>>) resultMap.get("data");
        } catch (Exception e){
            log.error(e.getMessage(), e.getCause());
        }
    }



//    public String runSftp(ChannelSftp sftpChannel, String folderPath) {
//        executeBatch(); // Generate CSV files
//
//        if (files.isEmpty()) {
//            log.warn("No files generated for upload");
//            return null;
//        }
//
//        try {
//            // Verify connection
//            if (!sftpChannel.isConnected()) {
//                throw new IllegalStateException("SFTP channel is not connected");
//            }
//
//            // Ensure target directory exists
//            try {
//                sftpChannel.ls(folderPath);
//            } catch (SftpException e) {
//                log.warn("Target directory doesn't exist, attempting to create: {}", folderPath);
//                mkdirs(sftpChannel, folderPath);
//            }
//
//            String pidPath = folderPath + "/" + configParams.getPid();
//            mkdirs(sftpChannel, pidPath);
//
//            // Upload files
//            for (String filename : files) {
//                File localFile = new File(baseDir + filename);
//
//                if (!localFile.exists()) {
//                    log.error("File not found: {}", localFile.getAbsolutePath());
//                    continue;
//                }
//
//                String remotePath = pidPath + "/" + filename;
//                log.info("Uploading {} to {}", localFile, remotePath);
//
//                try (FileInputStream fis = new FileInputStream(localFile)) {
//                    sftpChannel.put(fis, remotePath);
//                    log.info("Successfully uploaded: {}", remotePath);
//
//                    // Verify upload
//                    try {
//                        SftpATTRS attrs = sftpChannel.lstat(remotePath);
//                        log.info("Upload verification - file size: {}", attrs.getSize());
//                    } catch (SftpException e) {
//                        log.error("Upload verification failed for {}", remotePath, e);
//                    }
//
//                    // Delete local file only after successful upload
//                    if (!localFile.delete()) {
//                        log.warn("Failed to delete local file: {}", localFile);
//                    }
//                } catch (Exception e) {
//                    log.error("Failed to upload {}", filename, e);
//                }
//            }
//
//            return pidPath;
//        } catch (Exception e) {
//            log.error("SFTP upload process failed", e);
//            return null;
//        }
//    }


}
