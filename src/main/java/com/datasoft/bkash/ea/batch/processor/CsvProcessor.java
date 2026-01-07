package com.datasoft.bkash.ea.batch.processor;

import com.datasoft.bkash.ea.dao.JdbcFunctionDao;
import com.datasoft.bkash.ea.model.queryModule.ConfigParams;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class CsvProcessor {

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
    public CsvProcessor(JdbcFunctionDao jdbcFunctionDao, String procedureName, Integer batchSize, Integer chunkMultiplicationSize, String baseDir, ConfigParams configParams, Integer userId, Map<String, Object> spParams,String procedureSrc){
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

    public String run() {
        executeBatch();
        if(files.isEmpty()){
            return null;
        } else {
            ZipOutputStream zipOutput = null;
            try {
                String zipPath = UUID.randomUUID().toString().replace("-", "").toUpperCase().concat(".zip");
                zipOutput = new ZipOutputStream(Files.newOutputStream(Paths.get(baseDir + zipPath)));
                for(int part = 0; part < files.size(); part++){
                    FileInputStream fileInputStream = null;
                    try {
                        File file = new File(baseDir+files.get(part));
                        fileInputStream = new FileInputStream(file);
                        zipOutput.putNextEntry(new ZipEntry("Part_"+(part+1)+".csv"));
                        zipOutput.write(IOUtils.toByteArray(fileInputStream));
                        zipOutput.closeEntry();
                        fileInputStream.close();
                        FileUtils.forceDelete(file);
                    } catch (Exception e){
                        log.error(e.getMessage(), e.getCause());
                        if(Objects.nonNull(fileInputStream)) fileInputStream.close();
                    }
                }
                zipOutput.close();
                return zipPath;
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                if(Objects.nonNull(zipOutput)) {
                    try {
                        zipOutput.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        return null;
    }



    public String executeBatch(){
        while(true){
            Map<String,Object> inParam=this.spParams;
            inParam.put("paramLimit", batchSize);
            inParam.put("paramOffset", currentRow);
            try{
                HashMap<String, Object> resultMap = new HashMap<>();
                Map<String,Object> procedureResult;
                if(Objects.equals(procedureSrc, "slave")){
                    procedureResult = jdbcFunctionDao.getProcedureResultFromSlave(procedureName, inParam);
                }else{
                    procedureResult = jdbcFunctionDao.getProcedureResult(procedureName, inParam);
                }

                String result;

                if(procedureResult.containsKey("sequenceOfList") && !procedureResult.get("sequenceOfList").toString().isEmpty() && !procedureResult.get("sequenceOfList").toString().equals("query_log_website")) {
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
                    } else if( Objects.equals(this.procedureSrc, "Unmapped Product Type List") || Objects.equals(this.procedureSrc, "Unmapped Account To Entity Type List") || Objects.equals(this.procedureSrc, "Unmapped Account To Business Type List")|| (procedureResult.containsKey("headerSet") && procedureResult.get("headerSet")!=null) ){
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
                    }else if (procedureResult.containsKey("sequenceOfList")) {
                        headers = ((List<Map<String, Object>>) resultMap.get("headers")).stream().map(map -> map.get("visibleValue").toString()).collect(Collectors.toList());
                        columnHeaders = ((List<Map<String, Object>>) resultMap.get("headers")).stream().map(map -> map.get("actualValue").toString()).collect(Collectors.toList());
                    }else{
                        headers=Arrays.asList(((String)procedureResult.get("headerColumn")).split(","));
                        columnHeaders=Arrays.asList(((String)procedureResult.get("keySet")).split(","));
                    }
                    isHeaderSet = true;
                }
                results = (List<Map<String, Object>>) resultMap.get("data");
                if(results.isEmpty()){
                    break;
                }
                this.makeCSV();
            } catch (Exception e){
                log.error(e.getMessage(), e.getCause());
                break;
            }
        }
        return currentFile;
    }

    public void fetchData(){
            Map<String,Object> inParam=this.spParams;
            inParam.put("paramLimit", batchSize);
            inParam.put("paramOffset", currentRow);
            try{
                HashMap<String, Object> resultMap = new HashMap<>();
                Map<String,Object> procedureResult;
                if(Objects.equals(procedureSrc, "slave")){
                    procedureResult = jdbcFunctionDao.getProcedureResultFromSlave(procedureName, inParam);
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

    private String cleanCSV(String header) {
        return header.replace(",", ";");
    }

    private void makeCSV() throws IOException {
        currentFile = UUID.randomUUID().toString().replace("-", "").toUpperCase().concat(".csv");
        FileWriter csvWriter = new FileWriter(baseDir + currentFile, false);
        StringBuilder csvHeader = new StringBuilder();
        for(String header : headers){
            csvHeader.append("\"").append(header).append("\"").append(",");
        }
        csvWriter.append(csvHeader.toString());
        csvWriter.append("\n");
        boolean shouldIterationContinue=true;
        for(int i=0;i<chunkMultiplicationSize;i++) {
            if(i>0 && Objects.equals(shouldIterationContinue,true)){
                this.fetchData();
                if(results.size()>0){
                    shouldIterationContinue=true;
                }else{
                    shouldIterationContinue=false;
                }
                if(results.size()>0){
                    for (Map<String, Object> result : results) {
                        for (String header : columnHeaders) {
                            csvWriter.append("\"").append((Objects.nonNull(result.get(header)) ? result.get(header).toString() : "")).append("\"").append(",");
                        }
                        csvWriter.append("\n");
                    }
                    currentRow += results.size();
                    results.clear();
                }
            }else{
                if(results.size()>0){
                    shouldIterationContinue=true;
                }else{
                    shouldIterationContinue=false;
                }
                if(Objects.equals(shouldIterationContinue,true)) {
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
        csvWriter.close();
        files.add(currentFile);

    }

    public String fcaDataDownload(String type) {
        String returnResult=executeBatch();
        if(Objects.equals(returnResult,"No Data Found")){
            return returnResult;
        }
        if (files.isEmpty()) {
            return null;
        } else {
            ZipOutputStream zipOutput = null;
            try {
                String zipPath = type+"-"+UUID.randomUUID().toString().replace("-", "").toUpperCase().concat(".zip");
                zipOutput = new ZipOutputStream(Files.newOutputStream(Paths.get(baseDir + zipPath)));
                for (int part = 0; part < files.size(); part++) {
                    FileInputStream fileInputStream = null;
                    try {
                        File file = new File(baseDir + files.get(part));
                        fileInputStream = new FileInputStream(file);
                        zipOutput.putNextEntry(new ZipEntry("Part_" + (part + 1) + ".csv"));
                        zipOutput.write(IOUtils.toByteArray(fileInputStream));
                        zipOutput.closeEntry();
                        fileInputStream.close();
                        FileUtils.forceDelete(file);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e.getCause());
                        if (Objects.nonNull(fileInputStream)) fileInputStream.close();
                    }
                }
                zipOutput.close();
                return zipPath;
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                if (Objects.nonNull(zipOutput)) {
                    try {
                        zipOutput.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }finally {
                if (Objects.nonNull(zipOutput)) {
                    try {
                        zipOutput.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        return null;
    }



    public String createZip(List<String> files) {
        if (files.isEmpty()) {
            return null;
        } else {
            ZipOutputStream zipOutput = null;
            try {
                String zipPath = UUID.randomUUID().toString().replace("-", "").toUpperCase().concat(".zip");
                zipOutput = new ZipOutputStream(Files.newOutputStream(Paths.get(baseDir + zipPath)));
                for (int part = 0; part < files.size(); part++) {
                    FileInputStream fileInputStream = null;
                    try {
                        File file = new File(baseDir + files.get(part));
                        fileInputStream = new FileInputStream(file);
                        zipOutput.putNextEntry(new ZipEntry(file.getName()));
                        zipOutput.write(IOUtils.toByteArray(fileInputStream));
                        zipOutput.closeEntry();
                        fileInputStream.close();
                        FileUtils.forceDelete(file);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e.getCause());
                        if (Objects.nonNull(fileInputStream)) fileInputStream.close();
                    }
                }
                zipOutput.close();
                return zipPath;
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                if (Objects.nonNull(zipOutput)) {
                    try {
                        zipOutput.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }finally {
                if (Objects.nonNull(zipOutput)) {
                    try {
                        zipOutput.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        return null;
    }


}
